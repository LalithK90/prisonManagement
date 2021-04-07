package lk.prison_management.asset.performance_evaluation_approval.controller;

import lk.prison_management.asset.common_asset.model.enums.LiveOrDead;
import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.employee.service.EmployeeService;
import lk.prison_management.asset.employee_file.service.EmployeeFilesService;
import lk.prison_management.asset.performance_evaluation_request.entity.PerformanceEvaluationRequest;
import lk.prison_management.asset.performance_evaluation_request.entity.enums.Apprecial;
import lk.prison_management.asset.performance_evaluation_request.service.PerformanceEvaluationRequestService;
import lk.prison_management.asset.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/performanceEvaluationApproval" )
public class PerformanceEvaluationApprovalController {
  private final EmployeeService employeeService;
  private final EmployeeFilesService employeeFilesService;
  private final UserService userService;
  private final PerformanceEvaluationRequestService performanceEvaluationRequestService;

  public PerformanceEvaluationApprovalController(EmployeeService employeeService,
                                                 EmployeeFilesService employeeFilesService,
                                                 UserService userService,
                                                 PerformanceEvaluationRequestService performanceEvaluationRequestService) {
    this.employeeService = employeeService;
    this.employeeFilesService = employeeFilesService;
    this.userService = userService;
    this.performanceEvaluationRequestService = performanceEvaluationRequestService;
  }

  @RequestMapping
  public String employeePage(Model model) {
    Employee employeeUser =
        userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).getEmployee();
    List< Employee > employees = new ArrayList<>();

    for ( Employee employee : performanceEvaluationRequestService.findBySupervisor(employeeUser) ) {
      employee.setFileInfo(employeeFilesService.employeeFileDownloadLinks(employee));
      employees.add(employee);
    }
    model.addAttribute("employees", employees);
    return "performanceEvaluation/performanceEvaluation";
  }

  //Send on employee details
  @GetMapping( "/add" )
  public String employeeView(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Employee employee = userService.findByUserName(authentication.getName()).getEmployee();
    return commonThing(model, new PerformanceEvaluationRequest(), employee);
  }

  private String commonThing(Model model, PerformanceEvaluationRequest performanceEvaluationRequest,
                             Employee employee) {
    model.addAttribute("employeeDetail", employee);
    model.addAttribute("addStatus", true);
    model.addAttribute("files", employeeFilesService.employeeFileDownloadLinks(employee));
    model.addAttribute("performanceEvaluation", performanceEvaluationRequest);
    model.addAttribute("apprecials", Apprecial.values());
    return "performanceEvaluation/addPerformanceEvaluationApproval";
  }

  @GetMapping( "/confirm/{id}" )
  public String employeeConfirm(@PathVariable( "id" ) Integer id, Model model) {
    model.addAttribute("confirm", true);
    PerformanceEvaluationRequest performanceEvaluationRequest = performanceEvaluationRequestService.findById(id);
    Employee employee = employeeService.findById(performanceEvaluationRequest.getEmployee().getId());

    return commonThing(model, performanceEvaluationRequest, employee);
  }

  @PostMapping( "/save" )
  public String save(@ModelAttribute PerformanceEvaluationRequest performanceEvaluationRequest,
                     BindingResult bindingResult, Model model) {
    if ( bindingResult.hasErrors() ) {
      Employee employee = employeeService.findById(performanceEvaluationRequest.getEmployee().getId());
      commonThing(model, performanceEvaluationRequest, employee);
    }

//todo employee performance evaluation as need to add

    performanceEvaluationRequestService.persist(performanceEvaluationRequest);

    return "redirect:/employee";
  }
}
