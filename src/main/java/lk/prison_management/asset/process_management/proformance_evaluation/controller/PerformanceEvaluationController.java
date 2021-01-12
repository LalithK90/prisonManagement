package lk.prison_management.asset.process_management.proformance_evaluation.controller;

import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.employee.service.EmployeeService;
import lk.prison_management.asset.employee_file.service.EmployeeFilesService;
import lk.prison_management.asset.process_management.proformance_evaluation.entity.PerformanceEvaluation;
import lk.prison_management.asset.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/performanceEvaluation")
public class PerformanceEvaluationController {

  private final EmployeeService employeeService;
  private final EmployeeFilesService employeeFilesService;

  private final UserService userService;

  public PerformanceEvaluationController(EmployeeService employeeService, EmployeeFilesService employeeFilesService,
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
    model.addAttribute("addStatus", false);
    model.addAttribute("files", employeeFilesService.employeeFileDownloadLinks(employee));
    model.addAttribute("performanceEvaluation", new PerformanceEvaluation());
    return "performance/addPerformance";
  }

}
