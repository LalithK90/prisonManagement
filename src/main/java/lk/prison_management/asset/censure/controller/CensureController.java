package lk.prison_management.asset.censure.controller;

import lk.prison_management.asset.censure.entitiy.Censure;
import lk.prison_management.asset.censure.service.CensureService;
import lk.prison_management.asset.censure_file.entity.CensureFiles;
import lk.prison_management.asset.censure_file.service.CensureFilesService;
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
@RequestMapping("/censure")
public class CensureController implements AbstractController< Censure, Integer> {
    private final CensureService censureService;
    private final CensureFilesService censureFilesService;

    public CensureController(CensureService censureService,
                                  CensureFilesService censureFilesService) {
        this.censureService = censureService;
        this.censureFilesService = censureFilesService;
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

    @GetMapping("/add")
    public String form(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("prisonTypes", OffenceType.values());
        model.addAttribute("censure", new Censure());
        return "censure/addCommendation";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("addStatus", false);
        model.addAttribute("prisonTypes", OffenceType.values());
        model.addAttribute("censure", censureService.findById(id));
        return "censure/addCommendation";
    }

    @PostMapping(value = {"/save", "/update"})
    public String persist(@Valid @ModelAttribute Censure censure, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("addStatus", true);
            model.addAttribute("prisonTypes", OffenceType.values());
            model.addAttribute("censure", censure);
            return "censure/addCommendation";
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
