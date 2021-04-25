package lk.prison_management.asset.institute.controller;

import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.institute.entity.enums.PrisonType;
import lk.prison_management.asset.institute.entity.Institute;
import lk.prison_management.asset.institute.service.InstituteService;
import lk.prison_management.util.interfaces.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/institute")
public class InstituteController implements AbstractController<Institute, Integer> {
    private final InstituteService instituteService;

    public InstituteController(InstituteService instituteService) {
        this.instituteService = instituteService;
    }


    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("institutes", instituteService.findAll());
        return "institute/institute";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        model.addAttribute("instituteDetail", instituteService.findById(id));
        return "institute/institute-detail";
    }

    @GetMapping("/add")
    public String form(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("prisonTypes", PrisonType.values());
        model.addAttribute("institute", new Institute());
        return "institute/addInstitute";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("addStatus", false);
        model.addAttribute("prisonTypes", PrisonType.values());
        model.addAttribute("institute", instituteService.findById(id));
        return "institute/addInstitute";
    }

    @PostMapping(value = {"/save", "/update"})
    public String persist(@Valid @ModelAttribute Institute institute, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        Institute instititeName = null;
        if ( institute.getName() != null && institute.getId() == null ) {
            instititeName = instituteService.findByName(institute.getName());
        }
        if ( instititeName != null ) {
            ObjectError error = new ObjectError("employee",
                    "There is employee on same nic number . <br> System message -->");
            bindingResult.addError(error);
        }



        if (bindingResult.hasErrors()) {
//            System.out.println(" sadasd ");
            model.addAttribute("addStatus", true);
            model.addAttribute("prisonTypes", PrisonType.values());
            model.addAttribute("institute", institute);
            return "institute/addInstitute";
        }
        redirectAttributes.addFlashAttribute("instituteDetail", instituteService.persist(institute));
        return "redirect:/institute";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        instituteService.delete(id);
        return "redirect:/institute";
    }
}