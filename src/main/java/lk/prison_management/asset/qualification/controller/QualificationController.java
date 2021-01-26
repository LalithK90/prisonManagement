package lk.prison_management.asset.qualification.controller;


import lk.prison_management.asset.employee.service.EmployeeService;
import lk.prison_management.asset.qualification.entity.Qualification;
import lk.prison_management.asset.qualification.service.QualificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping( "/qualification" )
public class QualificationController {

  private final QualificationService qualificationService;
  private final EmployeeService employeeService;

  @Autowired
  public QualificationController(QualificationService qualificationService,
                                 EmployeeService employeeService) {
    this.qualificationService = qualificationService;
    this.employeeService = employeeService;
  }

  private String commonThing(Model model, Boolean booleanValue, Qualification qualificationObject) {
    model.addAttribute("addStatus", booleanValue);
    model.addAttribute("qualification", qualificationObject);
    return "qualification/addQualification";
  }

  @GetMapping
  public String findAll(Model model) {
    model.addAttribute("qualifications", qualificationService.findAll());
    return "qualification/qualification";
  }

  @GetMapping( "/add/{id}" )
  public String form(@PathVariable Integer id, Model model) {
model.addAttribute("employeeDetail", employeeService.findById(id));
    return commonThing(model, true, new Qualification());
  }

  @GetMapping( "/{id}" )
  public String findById(@PathVariable Integer id, Model model) {
    Qualification qualification = qualificationService.findById(id);
    model.addAttribute("qualificationDetail", qualification);
    model.addAttribute("employeeDetail", qualification.getEmployee());
    return "qualification/qualification-detail";
  }

  @GetMapping( "/edit/{id}" )
  public String edit(@PathVariable Integer id, Model model) {
    Qualification qualification = qualificationService.findById(id);
    model.addAttribute("employeeDetail", qualification.getEmployee());
    return commonThing(model, false, qualification);
  }

  @PostMapping( value = {"/save", "/update"} )
  public String persist(@Valid @ModelAttribute Qualification qualification, BindingResult bindingResult,
                        Model model) {
    if ( bindingResult.hasErrors() ) {
      model.addAttribute("employeeDetail", employeeService.findById( qualification.getEmployee().getId()));
      return commonThing(model, true, qualification);
    }
    qualificationService.persist(qualification);
    return "redirect:/qualification";
  }

  @GetMapping( "/delete/{id}" )
  public String delete(@PathVariable Integer id, Model model) {
    qualificationService.delete(id);
    return "redirect:/qualification";
  }


}
