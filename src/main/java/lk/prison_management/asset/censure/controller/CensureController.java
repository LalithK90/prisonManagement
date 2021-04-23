package lk.prison_management.asset.censure.controller;

import lk.prison_management.asset.censure.entitiy.Censure;
import lk.prison_management.asset.censure.service.CensureService;
import lk.prison_management.asset.censure_file.entity.CensureFiles;
import lk.prison_management.asset.censure_file.service.CensureFilesService;
import lk.prison_management.asset.employee.service.EmployeeService;
import lk.prison_management.asset.offence.controller.OffenceController;
import lk.prison_management.asset.offence.entity.enums.OffenceType;
import lk.prison_management.asset.offence.service.OffenceService;
import lk.prison_management.asset.punishment.controller.PunishmentController;
import lk.prison_management.asset.punishment.service.PunishmentService;
import lk.prison_management.util.interfaces.AbstractController;
import lk.prison_management.util.service.MakeAutoGenerateNumberService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequestMapping( "/censure" )
public class CensureController implements AbstractController< Censure, Integer > {
  private final CensureService censureService;
  private final CensureFilesService censureFilesService;
  private final EmployeeService employeeService;
  private final PunishmentService punishmentService;
  private final OffenceService offenceService;
  private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;

  public CensureController(CensureService censureService,
                           CensureFilesService censureFilesService, EmployeeService employeeService,
                           PunishmentService punishmentService, OffenceService offenceService,
                           MakeAutoGenerateNumberService makeAutoGenerateNumberService) {
    this.censureService = censureService;
    this.censureFilesService = censureFilesService;
    this.employeeService = employeeService;
    this.punishmentService = punishmentService;
    this.offenceService = offenceService;
    this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
  }


  @GetMapping
  public String findAll(Model model) {
    model.addAttribute("censures", censureService.findAll());
    return "censure/censure";
  }

  //When scr called file will send to
  @GetMapping( "/file/{filename}" )
  public ResponseEntity< byte[] > downloadFile(@PathVariable( "filename" ) String filename) {
    CensureFiles file = censureFilesService.findByNewID(filename);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
        .body(file.getPic());
  }


  @GetMapping( "/{id}" )
  public String findById(@PathVariable Integer id, Model model) {
    model.addAttribute("commendationDetail", censureService.findById(id));
    return "censure/censure-detail";
  }

  @GetMapping( "/add/{id}" )
  public String form(@PathVariable( "id" ) Integer id, Model model) {
    Censure censure = new Censure();
    censure.setEmployee(employeeService.findById(id));
    return commonAddCensure(model, censure, true);
  }

  private String commonAddCensure(Model model, Censure censure, boolean addStatus) {

    model.addAttribute("addStatus", addStatus);
    model.addAttribute("offenceTypes", OffenceType.values());
    model.addAttribute("employeeDetail", censure.getEmployee());
    model.addAttribute("censure", censure);
    model.addAttribute("punishmentFindUrl", MvcUriComponentsBuilder
        .fromMethodName(PunishmentController.class, "findByOffence", "")
        .toUriString());
    model.addAttribute("offenceUrl", MvcUriComponentsBuilder
        .fromMethodName(OffenceController.class, "findByOffenceType", "")
        .toUriString());
    model.addAttribute("punishmentUrl", MvcUriComponentsBuilder
        .fromMethodName(PunishmentController.class, "findByOffenceType", "")
        .toUriString());
    if ( !addStatus ) {
      model.addAttribute("file", censureFilesService.censureFileDownloadLinks(censure));
    }

    return "censure/addCensure";
  }

  @GetMapping( "/edit/{id}" )
  public String edit(@PathVariable Integer id, Model model) {
    Censure censure = censureService.findById(id);
    return commonAddCensure(model, censure, false);
  }

  @PostMapping( value = {"/save", "/update"} )
  public String persist(@Valid @ModelAttribute Censure censure, BindingResult bindingResult,
                        RedirectAttributes redirectAttributes, Model model) {
    if ( bindingResult.hasErrors() ) {
      return commonAddCensure(model, censure, true);
    }

    if ( censure.getId() == null ) {
      Censure lastCensure = censureService.lastCensure();
      if ( lastCensure == null ) {
        censure.setRefNumber("SLPC" + makeAutoGenerateNumberService.numberAutoGen(null).toString());
      } else {
        censure.setRefNumber("SLPC" + makeAutoGenerateNumberService.numberAutoGen(lastCensure.getRefNumber().substring(4)).toString());
      }
    }

    Censure censureSaved = censureService.persist(censure);

    try {
      //save censure images file
      if ( censure.getFile().getOriginalFilename() != null ) {
        CensureFiles censureFiles = censureFilesService.findByEmployee(censureSaved);
        if ( censureFiles != null ) {
          // update new contents
          censureFiles.setPic(censure.getFile().getBytes());
          // Save all to database
        } else {
          censureFiles = new CensureFiles(censure.getFile().getOriginalFilename(),
                                          censure.getFile().getContentType(),
                                          censure.getFile().getBytes(),
                                          censure.getEmployee().getNic().concat("-" + LocalDateTime.now()),
                                          UUID.randomUUID().toString().concat("censure"));
          censureFiles.setCensure(censure);
        }
        censureFilesService.persist(censureFiles);
      }
      censure = censureSaved;
      return "redirect:/censure";

    } catch ( Exception e ) {
      ObjectError error = new ObjectError("censure",
                                          "There is already in the system. <br>System message -->" + e.toString());
      bindingResult.addError(error);
      boolean addStatus = censure.getId() != null;

      return commonAddCensure(model, censure, addStatus);
    }

  }

  @GetMapping( "/delete/{id}" )
  public String delete(@PathVariable Integer id, Model model) {
    censureService.delete(id);
    return "redirect:/censure";
  }
}
