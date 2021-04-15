package lk.prison_management.asset.process_management;

import lk.prison_management.asset.censure.service.CensureService;
import lk.prison_management.asset.commendation.service.CommendationService;
import lk.prison_management.asset.common_asset.model.TwoDate;
import lk.prison_management.asset.common_asset.model.enums.LiveOrDead;
import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.employee.service.EmployeeService;
import lk.prison_management.asset.employee_file.service.EmployeeFilesService;
import lk.prison_management.asset.employee_leave.service.EmployeeLeaveService;
import lk.prison_management.asset.institute.service.InstituteService;
import lk.prison_management.asset.offence.entity.Offence;
import lk.prison_management.asset.offence.service.OffenceService;
import lk.prison_management.asset.performance_evaluation_request.service.PerformanceEvaluationRequestService;
import lk.prison_management.asset.process_management.mode.InstituteEmployee;
import lk.prison_management.asset.qualification.service.QualificationService;
import lk.prison_management.util.service.DateTimeAgeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/report" )
public class ReportController {
  private final EmployeeService employeeService;
  private final InstituteService instituteService;
  private final DateTimeAgeService dateTimeAgeService;
  private final EmployeeFilesService employeeFilesService;
  private final OffenceService offenceService;
  private final QualificationService qualificationService;
  private final EmployeeLeaveService employeeLeaveService;
  private final CommendationService commendationService;
  private final PerformanceEvaluationRequestService performanceEvaluationRequestService;
  private final CensureService censureService;

  public ReportController(EmployeeService employeeService, InstituteService instituteService,
                          QualificationService qualificationService, EmployeeLeaveService employeeLeaveService,
                          CommendationService commendationService,
                          PerformanceEvaluationRequestService performanceEvaluationRequestService,
                          CensureService censureService, DateTimeAgeService dateTimeAgeService,
                          EmployeeFilesService employeeFilesService, OffenceService offenceService) {
    this.employeeService = employeeService;
    this.instituteService = instituteService;
    this.qualificationService = qualificationService;
    this.employeeLeaveService = employeeLeaveService;
    this.commendationService = commendationService;
    this.performanceEvaluationRequestService = performanceEvaluationRequestService;
    this.censureService = censureService;
    this.dateTimeAgeService = dateTimeAgeService;
    this.employeeFilesService = employeeFilesService;
    this.offenceService = offenceService;
  }

  @GetMapping( "/instituteEmployee" )
  public String instituteEmployees(Model model) {
    List< InstituteEmployee > instituteEmployees = new ArrayList<>();
    instituteService.findAll().forEach(x -> {
      InstituteEmployee instituteEmployee = new InstituteEmployee();
      instituteEmployee.setInstitute(x);
      List< Employee > employees = new ArrayList<>();
      for ( Employee employee : employeeService.findByInstitute(x)
      ) {
        employee.setFileInfo(employeeFilesService.employeeFileDownloadLinks(employee));
        employees.add(employee);
      }
      instituteEmployee.setEmployees(employees);
      instituteEmployees.add(instituteEmployee);
    });

    model.addAttribute("instituteEmployees", instituteEmployees);

    return "report/instituteEmployee";
  }

  @GetMapping( "/employeeAllCount" )
  public String employeeAllCount(Model model) {
    return commonEmployeeAllCount(model, employeeService.findAll());
  }

  @PostMapping( "/employeeAllCount" )
  public String employeeAllCountSearch(@ModelAttribute TwoDate twoDate, Model model) {
    return commonEmployeeAllCount(model,
                                  employeeService.findByCreatedAtBetween(dateTimeAgeService.dateTimeToLocalDateStartInDay(twoDate.getStartDate()), dateTimeAgeService.dateTimeToLocalDateEndInDay(twoDate.getEndDate())));
  }

  private String commonEmployeeAllCount(Model model, List< Employee > employeesRequest) {
    model.addAttribute("twoDate", new TwoDate());
    List< Employee > employees = new ArrayList<>();
    for ( Employee employee : employeesRequest
        .stream()
        .filter(x -> LiveOrDead.ACTIVE.equals(x.getLiveOrDead()))
        .collect(Collectors.toList())
    ) {
      employee.setFileInfo(employeeFilesService.employeeFileDownloadLinks(employee));
      employees.add(employee);
    }
    model.addAttribute("employees", employees);
    return "report/employeeAllCount";
  }


//  offence and employee count

  @GetMapping( "/offenceCount" )
  public String offenceEmployee(Model model) {
    model.addAttribute("offences", offenceService.findAll());
    return "report/offenceReport";
  }

  @GetMapping( "/offenceCount/{id}" )
  public String offenceEmployeeSearch(@PathVariable( "id" ) Integer id, Model model) {
    List< Employee > employees = new ArrayList<>();
    Offence offence = offenceService.findById(id);
    model.addAttribute("offenceDetail", offence);
    censureService.findByOffence(offence).forEach(x -> employees.add(x.getEmployee()));
    model.addAttribute("employees", employees);
    return "report/offenceEmployee";
  }



}
