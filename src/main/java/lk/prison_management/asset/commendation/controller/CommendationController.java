package lk.prison_management.asset.commendation.controller;


import lk.prison_management.asset.commendation.entity.Commendation;
import lk.prison_management.asset.commendation.service.CommendationService;
import lk.prison_management.asset.commondation_file.entity.CommendationFiles;
import lk.prison_management.asset.commondation_file.service.CommendationFilesService;
import lk.prison_management.asset.commondation_file.entity.CommendationFiles;
import lk.prison_management.asset.employee.service.EmployeeService;
import lk.prison_management.asset.offence.entity.enums.OffenceType;
import lk.prison_management.asset.user.entity.User;
import lk.prison_management.util.interfaces.AbstractController;
import lk.prison_management.util.service.MakeAutoGenerateNumberService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequestMapping("/commendation")
public class CommendationController implements AbstractController< Commendation, Integer> {
    private final CommendationService commendationService;
    private final CommendationFilesService commendationFilesService;
    private final EmployeeService employeeService;
    private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;

    public CommendationController(CommendationService commendationService,
                                  CommendationFilesService commendationFilesService, EmployeeService employeeService,
                                  MakeAutoGenerateNumberService makeAutoGenerateNumberService) {
        this.commendationService = commendationService;
        this.commendationFilesService = commendationFilesService;
        this.employeeService = employeeService;
        this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
    }


    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("offences", commendationService.findAll());
        return "commendation/commendation";
    }

    //When scr called file will send to
    @GetMapping( "/file/{filename}" )
    public ResponseEntity< byte[] > downloadFile(@PathVariable( "filename" ) String filename) {
        CommendationFiles file = commendationFilesService.findByNewID(filename);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
            .body(file.getPic());
    }


    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        model.addAttribute("commendationDetail", commendationService.findById(id));
        return "commendation/commendation-detail";
    }

    @GetMapping("/add/{id}")
    public String form(@PathVariable("id")Integer id , Model model) {
        model.addAttribute("employeeDetail", employeeService.findById(id));
        model.addAttribute("addStatus", true);
        model.addAttribute("prisonTypes", OffenceType.values());
        model.addAttribute("commendation", new Commendation());
        return "commendation/addCommendation";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Commendation commendation = commendationService.findById(id);
        model.addAttribute("addStatus", false);
        model.addAttribute("prisonTypes", OffenceType.values());
        model.addAttribute("commendation", commendation);
        model.addAttribute("employeeDetail", commendation.getEmployee());
        model.addAttribute("file", commendationFilesService.employeeFileDownloadLinks(commendation));

        return "commendation/addCommendation";
    }

    @PostMapping(value = {"/save", "/update"})
    public String persist(@Valid @ModelAttribute Commendation commendation, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("addStatus", true);
            model.addAttribute("prisonTypes", OffenceType.values());
            model.addAttribute("commendation", commendation);
            model.addAttribute("employeeDetail", employeeService.findById(commendation.getEmployee().getId()));
            return "commendation/addCommendation";
        }
        if ( commendation.getId() == null ) {
            Commendation lastCommendation = commendationService.lastCommendation();
            if ( lastCommendation == null ) {
                commendation.setRefNumber("SLPC" + makeAutoGenerateNumberService.numberAutoGen(null).toString());
            } else {
                commendation.setRefNumber("SLPC" + makeAutoGenerateNumberService.numberAutoGen(lastCommendation.getRefNumber().substring(4)).toString());
            }
        }

        Commendation commendationSaved = commendationService.persist(commendation);
        try {
            //save employee images file
            if ( commendation.getFile().getOriginalFilename() != null ) {
                CommendationFiles commendationFile = commendationFilesService.findByCommendation(commendationSaved);
                if ( commendationFile != null ) {
                    // update new contents
                    commendationFile.setPic(commendation.getFile().getBytes());
                    // Save all to database
                } else {
                    commendationFile = new CommendationFiles(commendation.getFile().getOriginalFilename(),
                                                      commendation.getFile().getContentType(),
                                                      commendation.getFile().getBytes(),
                                                       LocalDateTime.now().toString(),
                                                      UUID.randomUUID().toString().concat("commendation"));
                    commendationFile.setCommendation(commendation);
                }
                commendationFilesService.persist(commendationFile);
            }
            commendation = commendationSaved;
            return "redirect:/commendation";

        } catch ( Exception e ) {
            ObjectError error = new ObjectError("commendation",
                                                "There is already in the system. <br>System message -->" + e.toString());
            bindingResult.addError(error);
            if ( commendation.getId() != null ) {
                model.addAttribute("addStatus", true);
                System.out.println("id is null");
            } else {
                model.addAttribute("addStatus", false);
            }
            model.addAttribute("commendation", commendation);
            return "commendation/addCommendation";
        }
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        commendationService.delete(id);
        return "redirect:/commendation";
    }
}
