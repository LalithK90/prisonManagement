package lk.prison_management.asset.punishment.controller;

import lk.prison_management.asset.punishment.entity.enums.PunishmentType;
import lk.prison_management.asset.punishment.entity.Punishment;
import lk.prison_management.asset.punishment.service.PunishmentService;
import lk.prison_management.util.interfaces.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/punishment")
public class PunishmentController implements AbstractController<Punishment, Integer> {
    private final PunishmentService punishmentService;

    public PunishmentController(PunishmentService punishmentService) {
        this.punishmentService = punishmentService;
    }


    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("punishments", punishmentService.findAll());
        return "punishment/punishment";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        model.addAttribute("punishmentDetail", punishmentService.findById(id));
        return "punishment/punishment-detail";
    }

    @GetMapping("/add")
    public String form(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("punishmentTypes", PunishmentType.values());
        model.addAttribute("punishment", new Punishment());
        return "punishment/addPunishment";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("addStatus", false);
        model.addAttribute("punishmentTypes", PunishmentType.values());
        model.addAttribute("punishment", punishmentService.findById(id));
        return "punishment/addPunishment";
    }

    @PostMapping(value = {"/save", "/update"})
    public String persist(@Valid @ModelAttribute Punishment punishment, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("addStatus", true);
            model.addAttribute("punishmentTypes", PunishmentType.values());
            model.addAttribute("punishment", punishment);
            return "punishment/addPunishment";
        }
        redirectAttributes.addFlashAttribute("punishmentDetail", punishmentService.persist(punishment));
        return "redirect:/punishment";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        punishmentService.delete(id);
        return "redirect:/punishment";
    }
}
