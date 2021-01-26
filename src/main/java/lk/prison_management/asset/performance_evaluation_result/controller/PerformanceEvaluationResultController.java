package lk.prison_management.asset.performance_evaluation_result.controller;

import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.employee.service.EmployeeService;
import lk.prison_management.asset.employee_file.service.EmployeeFilesService;
import lk.prison_management.asset.performance_evaluation.entity.PerformanceEvaluation;
import lk.prison_management.asset.performance_evaluation_result.entity.PerformanceEvaluationResult;
import lk.prison_management.asset.performance_evaluation_result.entity.enums.Apprecial;
import lk.prison_management.asset.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/performanceEvaluationResult")
public class PerformanceEvaluationResultController {

  private final EmployeeService employeeService;
  private final EmployeeFilesService employeeFilesService;

  private final UserService userService;

  public PerformanceEvaluationResultController(EmployeeService employeeService, EmployeeFilesService employeeFilesService,
                                               UserService userService) {
    this.employeeService = employeeService;
    this.employeeFilesService = employeeFilesService;
    this.userService = userService;
  }


  //Send on employee details
  @GetMapping("/add")
  public String employeeView( Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    Employee employee = userService.findByUserName(authentication.getName()).getEmployee();
    model.addAttribute("employeeDetail", employee);
    model.addAttribute("addStatus", true);
    model.addAttribute("files", employeeFilesService.employeeFileDownloadLinks(employee));
    model.addAttribute("performanceEvaluationResult", new PerformanceEvaluationResult());
    model.addAttribute("apprecials", Apprecial.values());
    return "performanceEvaluationResult/addPerformanceEvaluationResult";
  }
//todo-> continued
}
