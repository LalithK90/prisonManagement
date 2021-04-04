package lk.prison_management.asset.performance_evaluation.controller;

import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.employee.service.EmployeeService;
import lk.prison_management.asset.employee_file.service.EmployeeFilesService;
import lk.prison_management.asset.performance_evaluation.entity.PerformanceEvaluation;
import lk.prison_management.asset.performance_evaluation.entity.enums.Apprecial;
import lk.prison_management.asset.performance_evaluation.service.PerformanceEvaluationService;
import lk.prison_management.asset.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping( "/performanceEvaluation" )
public class PerformanceEvaluationController {

  private final EmployeeService employeeService;
  private final EmployeeFilesService employeeFilesService;

  private final UserService userService;
  private final PerformanceEvaluationService performanceEvaluationService;

  public PerformanceEvaluationController(EmployeeService employeeService,
                                         EmployeeFilesService employeeFilesService,
                                         UserService userService,
                                         PerformanceEvaluationService performanceEvaluationService) {
    this.employeeService = employeeService;
    this.employeeFilesService = employeeFilesService;
    this.userService = userService;
    this.performanceEvaluationService = performanceEvaluationService;
  }


  //Send on employee details
  @GetMapping( "/add" )
  public String employeeView(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Employee employee = userService.findByUserName(authentication.getName()).getEmployee();
    return commonThing(model, new PerformanceEvaluation(), employee);
  }

  private String commonThing(Model model, PerformanceEvaluation performanceEvaluation, Employee employee) {
    model.addAttribute("employeeDetail", employee);
    model.addAttribute("addStatus", true);
    model.addAttribute("files", employeeFilesService.employeeFileDownloadLinks(employee));
    model.addAttribute("performanceEvaluation", performanceEvaluation);
    model.addAttribute("apprecials", Apprecial.values());
    return "performanceEvaluation/addPerformanceEvaluation";
  }

  @GetMapping( "/confirm/{id}" )
  public String employeeConfirm(@PathVariable( "id" ) Integer id, Model model) {
    model.addAttribute("confirm", true);
    PerformanceEvaluation performanceEvaluation = performanceEvaluationService.findById(id);
    Employee employee = employeeService.findById(performanceEvaluation.getEmployee().getId());

    return commonThing(model, performanceEvaluation, employee);
  }

  @PostMapping( "/save" )
  public String save(@ModelAttribute PerformanceEvaluation performanceEvaluationResult,
                     BindingResult bindingResult, Model model) {
    if ( bindingResult.hasErrors() ) {

      Employee employee = employeeService.findById(performanceEvaluationResult.getEmployee().getId());
      commonThing(model, performanceEvaluationResult, employee);
    }
    performanceEvaluationService.persist(performanceEvaluationResult);
    return "redirect:/employee";
  }
}
