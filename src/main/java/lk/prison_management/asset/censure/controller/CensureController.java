package lk.prison_management.asset.censure.controller;

import lk.prison_management.asset.censure.entitiy.Censure;
import lk.prison_management.asset.censure.service.CensureService;
import lk.prison_management.asset.censure_file.entity.CensureFiles;
import lk.prison_management.asset.censure_file.service.CensureFilesService;
import lk.prison_management.asset.employee.service.EmployeeService;
import lk.prison_management.asset.offence.entity.enums.OffenceType;
import lk.prison_management.asset.offence.service.OffenceService;
import lk.prison_management.asset.punishment.service.PunishmentService;
import lk.prison_management.util.interfaces.AbstractController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/censure")
public class CensureController implements AbstractController< Censure, Integer> {
    private final CensureService censureService;
    private final CensureFilesService censureFilesService;
    private final EmployeeService employeeService;
    private final PunishmentService punishmentService;
    private final OffenceService offenceService;

    public CensureController(CensureService censureService,
                             CensureFilesService censureFilesService, EmployeeService employeeService,
                             PunishmentService punishmentService, OffenceService offenceService) {
        this.censureService = censureService;
        this.censureFilesService = censureFilesService;
        this.employeeService = employeeService;
        this.punishmentService = punishmentService;
        this.offenceService = offenceService;
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


    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        model.addAttribute("commendationDetail", censureService.findById(id));
        return "censure/censure-detail";
    }

    @GetMapping("/add/{id}")
    public String form(@PathVariable("id")Integer id , Model model) {
        model.addAttribute("employeeDetail", employeeService.findById(id));
        model.addAttribute("addStatus", true);
        model.addAttribute("prisonTypes", OffenceType.values());
        model.addAttribute("censure", new Censure());
        return "censure/addCensure";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("addStatus", false);
        model.addAttribute("prisonTypes", OffenceType.values());
        model.addAttribute("censure", censureService.findById(id));
        return "censure/addCensure";
    }

    @PostMapping(value = {"/save", "/update"})
    public String persist(@Valid @ModelAttribute Censure censure, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("addStatus", true);
            model.addAttribute("prisonTypes", OffenceType.values());
            model.addAttribute("censure", censure);
            return "censure/addCensure";
        }
        redirectAttributes.addFlashAttribute("commendationDetail", censureService.persist(censure));
        return "redirect:/censure";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        censureService.delete(id);
        return "redirect:/censure";
    }
}
