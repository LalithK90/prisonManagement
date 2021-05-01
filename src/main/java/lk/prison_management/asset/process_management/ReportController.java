package lk.prison_management.asset.process_management;

import lk.prison_management.asset.censure.entitiy.Censure;
import lk.prison_management.asset.censure.service.CensureService;
import lk.prison_management.asset.commendation.entity.Commendation;
import lk.prison_management.asset.commendation.service.CommendationService;
import lk.prison_management.asset.common_asset.model.TwoDate;
import lk.prison_management.asset.common_asset.model.enums.LiveOrDead;
import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.employee.service.EmployeeService;
import lk.prison_management.asset.employee_file.service.EmployeeFilesService;
import lk.prison_management.asset.employee_leave.service.EmployeeLeaveService;
import lk.prison_management.asset.institute.entity.Institute;
import lk.prison_management.asset.institute.service.InstituteService;
import lk.prison_management.asset.offence.entity.Offence;
import lk.prison_management.asset.offence.service.OffenceService;
import lk.prison_management.asset.performance_evaluation_request.service.PerformanceEvaluationRequestService;
import lk.prison_management.asset.process_management.mode.InstituteCensure;
import lk.prison_management.asset.process_management.mode.InstituteCommendation;
import lk.prison_management.asset.process_management.mode.InstituteEmployee;
import lk.prison_management.asset.qualification.service.QualificationService;
import lk.prison_management.util.service.DateTimeAgeService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

  @GetMapping( "/instituteCensure" )
  public String instituteCensure(Model model) {
    return "report/instituteCensure";

  }

  @PostMapping( "/instituteCensure" )
  public String instituteCensure(@ModelAttribute TwoDate twoDate, Model model) {
    List< Institute > institutes = instituteService.findAll();

    LocalDateTime startDateTime = dateTimeAgeService.dateTimeToLocalDateStartInDay(twoDate.getStartDate());
    LocalDateTime endDateTime = dateTimeAgeService.dateTimeToLocalDateEndInDay(twoDate.getEndDate());


    List< Censure > censures = censureService.findByCreatedAtIsBetween(startDateTime, endDateTime);
    //.stream().filter(x->x.getEmployee().getInstitute().equals(   )).collect(Collectors.toList());

    List< InstituteCensure > instituteCensures = new ArrayList<>();

    institutes.forEach(x -> {
      long count = 0;
      for ( Censure censure : censures ) {
        if ( censure.getEmployee().getInstitute().equals(x) ) {
          count = count + 1;
        }
      }
      InstituteCensure instituteCensure = new InstituteCensure();
      instituteCensure.setInstitute(x);
      instituteCensure.setCensureCount(count);
      instituteCensures.add(instituteCensure);
    });
    String message = "This report is from " + twoDate.getStartDate() + " to " + twoDate.getEndDate();
    model.addAttribute("message", message);
    model.addAttribute("instituteCensures", instituteCensures);
    return "report/instituteCensure";

  }
  @GetMapping( "/instituteCensureOne" )
  public String instituteCensureOne(Model model) {
    model.addAttribute("institutes", instituteService.findAll());
    return "report/instituteCensureOne";

  }

  @PostMapping( "/instituteCensureOne" )
  public String instituteCensureOne(@ModelAttribute TwoDate twoDate, Model model) {

    Institute institute = instituteService.findById(twoDate.getId());

    LocalDateTime startDateTime = dateTimeAgeService.dateTimeToLocalDateStartInDay(twoDate.getStartDate());
    LocalDateTime endDateTime = dateTimeAgeService.dateTimeToLocalDateEndInDay(twoDate.getEndDate());


    List< Censure > censures = censureService.findByCreatedAtIsBetween(startDateTime, endDateTime)
        .stream()
        .filter(x->x.getEmployee().getInstitute().equals(institute))
        .collect(Collectors.toList());


    String message = "This report is from " + twoDate.getStartDate() + " to " + twoDate.getEndDate();

    InstituteCensure instituteCensure = new InstituteCensure();
    instituteCensure.setInstitute(institute);
    instituteCensure.setCensureCount(censures.size());

    model.addAttribute("message", message);
    model.addAttribute("instituteCensure", instituteCensure);

    model.addAttribute("institutes", instituteService.findAll());
    return "report/instituteCensureOne";

  }

//aluth report ekak institute censure

  @GetMapping( "/instituteCommendation" )
  public String instituteCommendation(Model model) {
    model.addAttribute("institutes", instituteService.findAll());
    return "report/instituteCommendation";

  }

  @PostMapping( "/instituteCommendation" )
  public String instituteCommendation(@ModelAttribute TwoDate twoDate, Model model) {
    List< Institute > institutes = instituteService.findAll();


    //table eken data enwada balannwa
    System.out.println(twoDate.getStartDate());
    System.out.println(twoDate.getEndDate());
    //institute id eka
    System.out.println(twoDate.getId());

    LocalDateTime startDateTime = dateTimeAgeService.dateTimeToLocalDateStartInDay(twoDate.getStartDate());
    LocalDateTime endDateTime = dateTimeAgeService.dateTimeToLocalDateEndInDay(twoDate.getEndDate());

//commendation list eka genna gaththa
    List<Commendation> commendations = commendationService.findByCreatedAtIsBetween(startDateTime, endDateTime);
    //.stream().filter(x->x.getEmployee().getInstitute().equals(   )).collect(Collectors.toList());
    System.out.println(commendations);
    List<InstituteCommendation> instituteCommendations = new ArrayList<>();

    //eka institute eka bagin filter karnne getId  eken
    Institute institute=instituteService.findById(twoDate.getId());
    long count = 0;
    for ( Commendation commendation : commendations ) {
      //adala institute eke employee ta thiyena institute id eka deepu institute Id smanda blanawa
      if (commendation.getEmployee().getInstitute().equals(institute)) {
        count = count + 1;
      }


    }
    InstituteCommendation instituteCommendation = new InstituteCommendation();
    instituteCommendation.setInstitute(institute);
    instituteCommendation.setCommendationCount(count);
    instituteCommendations.add(instituteCommendation);



    model.addAttribute("institutes", instituteService.findAll());


    String message = "This report is from " + twoDate.getStartDate() + " to " + twoDate.getEndDate();
    model.addAttribute("message", message);
    model.addAttribute("instituteCommendations", instituteCommendations);
    return "report/instituteCommendation";

  }







}
