package lk.prison_management.asset.process_management;

import lk.prison_management.asset.censure.service.CensureService;
import lk.prison_management.asset.commendation.service.CommendationService;
import lk.prison_management.asset.employee.service.EmployeeService;
import lk.prison_management.asset.employee_leave.service.EmployeeLeaveService;
import lk.prison_management.asset.institute.service.InstituteService;
import lk.prison_management.asset.performance_evaluation_request.service.PerformanceEvaluationRequestService;
import lk.prison_management.asset.qualification.service.QualificationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping( "/report" )
public class ReportController {
  private final EmployeeService employeeService;
  private final InstituteService instituteService;
  private final QualificationService qualificationService;
  private final EmployeeLeaveService employeeLeaveService;
  private final CommendationService commendationService;
  private final PerformanceEvaluationRequestService performanceEvaluationRequestService;
  private final CensureService censureService;

  public ReportController(EmployeeService employeeService, InstituteService instituteService,
                          QualificationService qualificationService, EmployeeLeaveService employeeLeaveService,
                          CommendationService commendationService,
                          PerformanceEvaluationRequestService performanceEvaluationRequestService, CensureService censureService) {
    this.employeeService = employeeService;
    this.instituteService = instituteService;
    this.qualificationService = qualificationService;
    this.employeeLeaveService = employeeLeaveService;
    this.commendationService = commendationService;
    this.performanceEvaluationRequestService = performanceEvaluationRequestService;
    this.censureService = censureService;
  }

  @GetMapping
  public String allInOneReport(Model model) {
    return "report/allInOne";
  }
}
