package lk.prison_management.asset.employee_leave.controller;


import lk.prison_management.asset.employee.service.EmployeeService;
import lk.prison_management.asset.employee_leave.entity.EmployeeLeave;
import lk.prison_management.asset.employee_leave.entity.enums.LeaveType;
import lk.prison_management.asset.employee_leave.service.EmployeeLeaveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping( "/employeeLeave" )
public class EmployeeLeaveController {

  private final EmployeeLeaveService employeeLeaveService;
  private final EmployeeService employeeService;

  public EmployeeLeaveController(EmployeeLeaveService employeeLeaveService, EmployeeService employeeService) {
    this.employeeLeaveService = employeeLeaveService;
    this.employeeService = employeeService;
  }

  private String commonThing(Model model, Boolean booleanValue, EmployeeLeave employeeLeave) {
    model.addAttribute("addStatus", booleanValue);
    model.addAttribute("employeeLeave", employeeLeave);
    model.addAttribute("leaveTypes", LeaveType.values());
    return "employeeLeave/addEmployeeLeave";
  }

  @GetMapping
  public String findAll(Model model) {
    model.addAttribute("employeeLeaves", employeeLeaveService.findAll());
    return "employeeLeave/employeeLeave";
  }

  @GetMapping( "/add/{id}" )
  public String form(@PathVariable Integer id, Model model) {
model.addAttribute("employeeDetail", employeeService.findById(id));
    return commonThing(model, true, new EmployeeLeave());
  }

  @GetMapping( "/{id}" )
  public String findById(@PathVariable Integer id, Model model) {
    EmployeeLeave employeeLeave = employeeLeaveService.findById(id);
    model.addAttribute("employeeLeaveDetail", employeeLeave);
    model.addAttribute("employeeDetail", employeeLeave.getEmployee());
    return "employeeLeave/employeeLeave-detail";
  }

  @GetMapping( "/edit/{id}" )
  public String edit(@PathVariable Integer id, Model model) {
    return commonThing(model, false, employeeLeaveService.findById(id));
  }

  @PostMapping( value = {"/save", "/update"} )
  public String persist(@Valid @ModelAttribute EmployeeLeave employeeLeave, BindingResult bindingResult,
                        Model model) {
    if ( bindingResult.hasErrors() ) {
      model.addAttribute("employeeDetail", employeeService.findById( employeeLeave.getEmployee().getId()));
      return commonThing(model, true, employeeLeave);
    }
    employeeLeaveService.persist(employeeLeave);
    return "redirect:/employeeLeave";
  }

  @GetMapping( "/delete/{id}" )
  public String delete(@PathVariable Integer id, Model model) {
    employeeLeaveService.delete(id);
    return "redirect:/employeeLeave";
  }


}
