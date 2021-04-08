package lk.prison_management.asset.employee_institute.controller;

import lk.prison_management.asset.employee.controller.EmployeeController;
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
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping( "/employeeInstitute" )
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

  private String commonThing(Model model, Employee employee, EmployeeInstitute employeeInstitute, boolean addStatus) {
    model.addAttribute("employeeDetail", employee);
    model.addAttribute("employeeInstitute", employeeInstitute);
    model.addAttribute("addStatus", addStatus);
    model.addAttribute("institutes", instituteService.findAll());
    model.addAttribute("instituteChangeReasons", InstituteChangeReason.values());
    model.addAttribute("supervisorFindUrl", MvcUriComponentsBuilder
        .fromMethodName(EmployeeController.class, "findSupervisor", "")
        .toUriString());
    return "employeeInstitute/addEmployeeInstitute";
  }

  @GetMapping
  public String findAll(Model model) {
    model.addAttribute("employeeInstitutes", employeeInstituteService.findAll());
    return "employeeInstitute/employeeInstitute";
  }

  @GetMapping( "/add/{id}" )
  public String form(@PathVariable Integer id, Model model) {
    Employee employee = employeeService.findById(id);
    List< EmployeeInstitute > employeeInstituteList = employeeInstituteService.findByEmployee(employee);

    EmployeeInstitute employeeInstitute;
    if ( employeeInstituteList.size() > 1 ) {
      employeeInstitute = employeeInstituteList.get(employeeInstituteList.size() - 1);
    } else {
      employeeInstitute = employeeInstituteList.get(0);
    }

    return commonThing(model, employee, employeeInstitute, true);
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
    return commonThing(model, employeeInstitute.getEmployee(), employeeInstitute, false);
  }

  @PostMapping( value = {"/save", "/update"} )
  public String persist(@Valid @ModelAttribute EmployeeInstitute employeeInstitute, BindingResult bindingResult,
                        Model model) {
    if ( bindingResult.hasErrors() ) {
      Employee employee = employeeService.findById(employeeInstitute.getEmployee().getId());

      return commonThing(model, employee, employeeInstitute, true);
    }

    EmployeeInstitute employeeInstituteDb = employeeInstituteService.findById(employeeInstitute.getId());
    employeeInstituteDb.setEndAt(employeeInstitute.getEndAt());
    employeeInstituteDb.setInstituteChangeReason(employeeInstitute.getInstituteChangeReason());
    employeeInstituteService.persist(employeeInstituteDb);

    EmployeeInstitute employeeInstituteNew = new EmployeeInstitute();
    employeeInstituteNew.setEmployee(employeeInstitute.getEmployee());
    employeeInstituteNew.setInstitute(employeeInstitute.getInstitute());
    employeeInstituteNew.setStartAt(employeeInstitute.getEndAt());
    employeeInstituteNew.setInstituteChangeReason(InstituteChangeReason.IMPORTANCEOFSERVICE);
    employeeInstituteService.persist(employeeInstituteNew);

    if ( employeeInstitute.getSupervisor() != null ) {
      Employee employee = employeeService.findById(employeeInstitute.getEmployee().getId());
      employee.setSupervisor(employeeService.findById(employeeInstitute.getSupervisor().getId()));
      employeeService.persist(employee);
    }
    return "redirect:/employee";
  }

  @GetMapping( "/delete/{id}" )
  public String delete(@PathVariable Integer id, Model model) {
    employeeInstituteService.delete(id);
    return "redirect:/qualification";
  }


}

