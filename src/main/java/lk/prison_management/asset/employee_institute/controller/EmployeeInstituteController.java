package lk.prison_management.asset.employee_institute.controller;

import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.employee.service.EmployeeService;
import lk.prison_management.asset.employee_institute.entity.EmployeeInstitute;
import lk.prison_management.asset.employee_institute.service.EmployeeInstituteService;
import lk.prison_management.asset.institute.entity.enums.InstituteChangeReason;
import lk.prison_management.asset.institute.service.InstituteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/employeeInstitute")
public class EmployeeInstituteController {
  private final EmployeeService employeeService;
  private final InstituteService instituteService;
  private final EmployeeInstituteService employeeInstituteService;

  public EmployeeInstituteController(EmployeeService employeeService, InstituteService instituteService,
                                     EmployeeInstituteService employeeInstituteService) {
    this.employeeService = employeeService;
    this.instituteService = instituteService;
    this.employeeInstituteService = employeeInstituteService;
  }
  private String commonThing(Model model, Employee employee, EmployeeInstitute employeeInstitute, boolean addStatus){
    model.addAttribute("employeeDetail", employee);
    model.addAttribute("employeeInstitute", employeeInstitute);
    model.addAttribute("addStatus", addStatus);
    model.addAttribute("institutes", instituteService.findAll());
    model.addAttribute("instituteChangeReasons", InstituteChangeReason.values());
    return "employeeInstitute/addEmployeeInstitute";
  }
  @GetMapping
  public String findAll(Model model) {
    model.addAttribute("employeeInstitutes", employeeInstituteService.findAll());
    return "employeeInstitute/employeeInstitute";
  }

  @GetMapping( "/add/{id}" )
  public String form(@PathVariable Integer id, Model model) {
    return commonThing(model, employeeService.findById(id), new EmployeeInstitute(), true);
  }

  @GetMapping( "/{id}" )
  public String findById(@PathVariable Integer id, Model model) {
    EmployeeInstitute employeeInstitute = employeeInstituteService.findById(id);
    model.addAttribute("employeeInstituteDetail", employeeInstitute);
    model.addAttribute("employeeDetail", employeeInstitute.getEmployee());
    model.addAttribute("instituteDetail", employeeInstitute.getInstitute());
    return "employeeInstitute/employeeInstitute-detail";
  }

  @GetMapping( "/edit/{id}" )
  public String edit(@PathVariable Integer id, Model model) {
    EmployeeInstitute employeeInstitute = employeeInstituteService.findById(id);
    return commonThing(model, employeeInstitute.getEmployee(), employeeInstitute,false);
  }

  @PostMapping( value = {"/save", "/update"} )
  public String persist(@Valid @ModelAttribute EmployeeInstitute employeeInstitute, BindingResult bindingResult,
                        Model model) {
    if ( bindingResult.hasErrors() ) {
      Employee employee = employeeService.findById(employeeInstitute.getEmployee().getId());
      return commonThing(model, employee, employeeInstitute,true);
    }
    employeeInstituteService.persist(employeeInstitute);
    return "redirect:/employee";
  }

  @GetMapping( "/delete/{id}" )
  public String delete(@PathVariable Integer id, Model model) {
    employeeInstituteService.delete(id);
    return "redirect:/qualification";
  }


}

