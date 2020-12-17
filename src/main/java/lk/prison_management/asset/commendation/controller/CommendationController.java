package lk.prison_management.asset.commendation.controller;


import lk.prison_management.asset.commendation.entity.Commendation;
import lk.prison_management.asset.commendation.service.CommendationService;
import lk.prison_management.asset.commondation_file.entity.CommendationFiles;
import lk.prison_management.asset.commondation_file.service.CommendationFilesService;
import lk.prison_management.asset.offence.entity.enums.OffenceType;
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
@RequestMapping("/commendation")
public class CommendationController implements AbstractController< Commendation, Integer> {
    private final CommendationService commendationService;
    private final CommendationFilesService commendationFilesService;

    public CommendationController(CommendationService commendationService,
                                  CommendationFilesService commendationFilesService) {
        this.commendationService = commendationService;
        this.commendationFilesService = commendationFilesService;
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

    @GetMapping("/add")
    public String form(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("prisonTypes", OffenceType.values());
        model.addAttribute("commendation", new Commendation());
        return "commendation/addCommendation";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("addStatus", false);
        model.addAttribute("prisonTypes", OffenceType.values());
        model.addAttribute("commendation", commendationService.findById(id));
        return "commendation/addCommendation";
    }

    @PostMapping(value = {"/save", "/update"})
    public String persist(@Valid @ModelAttribute Commendation commendation, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("addStatus", true);
            model.addAttribute("prisonTypes", OffenceType.values());
            model.addAttribute("commendation", commendation);
            return "commendation/addCommendation";
        }
        redirectAttributes.addFlashAttribute("commendationDetail", commendationService.persist(commendation));
        return "redirect:/commendation";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        commendationService.delete(id);
        return "redirect:/commendation";
    }
}
