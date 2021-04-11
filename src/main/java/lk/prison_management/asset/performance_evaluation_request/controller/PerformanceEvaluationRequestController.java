package lk.prison_management.asset.performance_evaluation_request.controller;

import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.employee.service.EmployeeService;
import lk.prison_management.asset.employee_file.service.EmployeeFilesService;
import lk.prison_management.asset.performance_evaluation_request.entity.PerformanceEvaluationRequest;
import lk.prison_management.asset.performance_evaluation_request.entity.enums.Apprecial;
import lk.prison_management.asset.performance_evaluation_request.service.PerformanceEvaluationRequestService;
import lk.prison_management.asset.user.service.UserService;
import lk.prison_management.util.service.EmailService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;

@Controller
@RequestMapping( "/performanceEvaluationRequest" )
public class PerformanceEvaluationRequestController {
  private final EmployeeService employeeService;
  private final EmployeeFilesService employeeFilesService;
  private final UserService userService;
  private final PerformanceEvaluationRequestService performanceEvaluationRequestService;
  private final EmailService emailService;

  public PerformanceEvaluationRequestController(EmployeeService employeeService,
                                                EmployeeFilesService employeeFilesService,
                                                UserService userService,
                                                PerformanceEvaluationRequestService performanceEvaluationRequestService, EmailService emailService) {
    this.employeeService = employeeService;
    this.employeeFilesService = employeeFilesService;
    this.userService = userService;
    this.performanceEvaluationRequestService = performanceEvaluationRequestService;
    this.emailService = emailService;
  }

  //Send on employee details
  @GetMapping( "/add" )
  public String employeeView(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Employee employee = userService.findByUserName(authentication.getName()).getEmployee();

    int year = LocalDate.now().minusYears(1).getYear();
    LocalDate startDate = LocalDate.of(year, 1, 1);
    LocalDate endDate = LocalDate.of(year, 12, 1);

    PerformanceEvaluationRequest performanceEvaluationRequestDb =
        performanceEvaluationRequestService.findByEmployeeAndFormDateAndToDate(employee, startDate, endDate);
    if ( performanceEvaluationRequestDb != null ) {
      model.addAttribute("message", "You already have requested from " + startDate + " to " + endDate + ". \n Please " +
          "try next year ");
      return "performanceEvaluation/voilateRequest";
    }
    PerformanceEvaluationRequest performanceEvaluationRequest = new PerformanceEvaluationRequest();
    performanceEvaluationRequest.setFormDate(startDate);
    performanceEvaluationRequest.setToDate(endDate);
    return commonThing(model, performanceEvaluationRequest, employee);
  }

  private String commonThing(Model model, PerformanceEvaluationRequest performanceEvaluationRequest,
                             Employee employee) {
    model.addAttribute("employeeDetail", employee);
    model.addAttribute("addStatus", true);
    model.addAttribute("files", employeeFilesService.employeeFileDownloadLinks(employee));
    model.addAttribute("performanceEvaluationRequest", performanceEvaluationRequest);
    model.addAttribute("apprecials", Apprecial.values());
    return "performanceEvaluation/addPerformanceEvaluationRequest";
  }

  @GetMapping( "/confirm/{id}" )
  public String employeeConfirm(@PathVariable( "id" ) Integer id, Model model) {
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
    PerformanceEvaluationRequest performanceEvaluationRequestDb =
        performanceEvaluationRequestService.persist(performanceEvaluationRequest);

    Employee employee = employeeService.findById(performanceEvaluationRequestDb.getEmployee().getId());

    if ( employee.getSupervisor().getEmail() != null ) {
      String message = employee.getTitle().getTitle() + " " + employee.getName() + " has requested his performance " +
          "evaluation";
      emailService.sendEmail(employee.getSupervisor().getEmail(), "Inform About Performance Evaluation Request",
                             message);
    }

    return "redirect:/home";
  }
}
