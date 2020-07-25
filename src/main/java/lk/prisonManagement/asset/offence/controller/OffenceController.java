package lk.prisonManagement.asset.offence.controller;

import lk.prisonManagement.asset.offence.entity.Enum.OffenceType;
import lk.prisonManagement.asset.offence.entity.Offence;
import lk.prisonManagement.asset.offence.service.OffenceService;
import lk.prisonManagement.util.interfaces.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/offence")
public class OffenceController implements AbstractController<Offence, Integer> {
    private final OffenceService offenceService;

    public OffenceController(OffenceService offenceService) {
        this.offenceService = offenceService;
    }


    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("offences", offenceService.findAll());
        return "offence/offence";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        model.addAttribute("offenceDetail", offenceService.findById(id));
        return "offence/offence-detail";
    }

    @GetMapping("/add")
    public String form(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("prisonTypes", OffenceType.values());
        model.addAttribute("offence", new Offence());
        return "offence/addOffence";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("addStatus", false);
        model.addAttribute("prisonTypes", OffenceType.values());
        model.addAttribute("offence", offenceService.findById(id));
        return "offence/addOffence";
    }

    @PostMapping(value = {"/save", "/update"})
    public String persist(@Valid @ModelAttribute Offence offence, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("addStatus", true);
            model.addAttribute("prisonTypes", OffenceType.values());
            model.addAttribute("offence", offence);
            return "offence/addOffence";
        }
        redirectAttributes.addFlashAttribute("offenceDetail", offenceService.persist(offence));
        return "redirect:/offence";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        offenceService.delete(id);
        return "redirect:/offence";
    }
}