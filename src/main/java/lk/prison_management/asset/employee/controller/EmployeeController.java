package lk.prison_management.asset.employee.controller;


import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lk.prison_management.asset.censure.service.CensureService;
import lk.prison_management.asset.commendation.service.CommendationService;
import lk.prison_management.asset.common_asset.model.enums.*;
import lk.prison_management.asset.common_asset.service.CommonService;
import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.employee_file.entity.EmployeeFiles;
import lk.prison_management.asset.employee.entity.enums.Designation;
import lk.prison_management.asset.employee.entity.enums.EmployeeStatus;
import lk.prison_management.asset.employee_file.service.EmployeeFilesService;
import lk.prison_management.asset.employee.service.EmployeeService;
import lk.prison_management.asset.employee_institute.entity.EmployeeInstitute;
import lk.prison_management.asset.employee_institute.service.EmployeeInstituteService;
import lk.prison_management.asset.employee_leave.service.EmployeeLeaveService;
import lk.prison_management.asset.institute.entity.Institute;
import lk.prison_management.asset.institute.entity.enums.InstituteChangeReason;
import lk.prison_management.asset.institute.service.InstituteService;
import lk.prison_management.asset.performance_evaluation.service.PerformanceEvaluationService;
import lk.prison_management.asset.qualification.service.QualificationService;
import lk.prison_management.asset.user.entity.User;
import lk.prison_management.asset.user.service.UserService;
import lk.prison_management.util.service.DateTimeAgeService;
import lk.prison_management.util.service.MakeAutoGenerateNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/employee" )
public class EmployeeController {
  private final EmployeeService employeeService;
  private final EmployeeFilesService employeeFilesService;
  private final DateTimeAgeService dateTimeAgeService;
  private final CommonService commonService;
  private final UserService userService;
  private final EmployeeInstituteService employeeInstituteService;
  private final EmployeeLeaveService employeeLeaveService;
  private final QualificationService qualificationService;
  private final CommendationService commendationService;
  private final CensureService censureService;
  private final PerformanceEvaluationService performanceEvaluationService;
  private final InstituteService instituteService;
  private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;

  @Autowired
  public EmployeeController(EmployeeService employeeService, EmployeeFilesService employeeFilesService,
                            DateTimeAgeService dateTimeAgeService,
                            CommonService commonService, UserService userService,
                            EmployeeInstituteService employeeInstituteService,
                            EmployeeLeaveService employeeLeaveService, QualificationService qualificationService,
                            CommendationService commendationService, CensureService censureService,
                            PerformanceEvaluationService performanceEvaluationService,
                            InstituteService instituteService,
                            MakeAutoGenerateNumberService makeAutoGenerateNumberService) {
    this.employeeService = employeeService;
    this.employeeFilesService = employeeFilesService;
    this.dateTimeAgeService = dateTimeAgeService;
    this.commonService = commonService;
    this.userService = userService;
    this.employeeInstituteService = employeeInstituteService;
    this.employeeLeaveService = employeeLeaveService;
    this.qualificationService = qualificationService;
    this.commendationService = commendationService;
    this.censureService = censureService;
    this.performanceEvaluationService = performanceEvaluationService;
    this.instituteService = instituteService;
    this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
  }

  private String commonThings(Model model) {
    model.addAttribute("title", Title.values());
    model.addAttribute("gender", Gender.values());
    model.addAttribute("civilStatus", CivilStatus.values());
    model.addAttribute("employeeStatus", EmployeeStatus.values());
    model.addAttribute("designation", Designation.values());
    model.addAttribute("bloodGroup", BloodGroup.values());
    model.addAttribute("supervisorFindUrl", MvcUriComponentsBuilder
        .fromMethodName(EmployeeController.class, "findSupervisor", "")
        .toUriString());
    model.addAttribute("institutes", instituteService.findAll());
    return "employee/addEmployee";
  }

  //When scr called file will send to
  @GetMapping( "/file/{filename}" )
  public ResponseEntity< byte[] > downloadFile(@PathVariable( "filename" ) String filename) {
    EmployeeFiles file = employeeFilesService.findByNewID(filename);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
        .body(file.getPic());
  }

  //Send all employee data
  @RequestMapping
  public String employeePage(Model model) {
    List< Employee > employees = new ArrayList<>();
    for ( Employee employee : employeeService.findAll()
        .stream()
        .filter(x -> LiveOrDead.ACTIVE.equals(x.getLiveOrDead()))
        .collect(Collectors.toList())
    ) {
      employee.setFileInfo(employeeFilesService.employeeFileDownloadLinks(employee));
      employees.add(employee);
    }
    model.addAttribute("employees", employees);
    model.addAttribute("contendHeader", "Employee");
    return "employee/employee";
  }

  //Send on employee details
  @GetMapping( value = "/{id}" )
  public String employeeView(@PathVariable( "id" ) Integer id, Model model) {
    Employee employee = employeeService.findById(id);
    model.addAttribute("employeeDetail", employee);
    model.addAttribute("addStatus", false);
    model.addAttribute("contendHeader", "Employee View Details");
    model.addAttribute("files", employeeFilesService.employeeFileDownloadLinks(employee));
    return "employee/employee-detail";
  }

  //Send employee data edit
  @GetMapping( value = "/edit/{id}" )
  public String editEmployeeForm(@PathVariable( "id" ) Integer id, Model model) {
    Employee employee = employeeService.findById(id);
    employee.setPerformanceEvaluations(performanceEvaluationService.findByEmployee(employee));
    employee.setCensures(censureService.findByEmployee(employee));
    employee.setCommendations(commendationService.findByEmployee(employee));
    employee.setQualifications(qualificationService.findByEmployee(employee));
    employee.setEmployeeLeaves(employeeLeaveService.findByEmployee(employee));
    employee.setEmployeeInstitutes(employeeInstituteService.findByEmployee(employee));

    model.addAttribute("employee", employee);
    model.addAttribute("addStatus", false);
    model.addAttribute("contendHeader", "Employee Edit Details");
    model.addAttribute("file", employeeFilesService.employeeFileDownloadLinks(employee));
    return commonThings(model);
  }

  //Send an employee add form
  @GetMapping( value = {"/add"} )
  public String employeeAddForm(Model model) {
    model.addAttribute("addStatus", true);
    model.addAttribute("employee", new Employee());
    model.addAttribute("contendHeader", "Employee Add Members");
    return commonThings(model);
  }

  //Employee add and update
  @PostMapping( value = {"/save", "/update"} )
  public String addEmployee(@Valid @ModelAttribute Employee employee, BindingResult result, Model model
                           ) {
    Employee employeeDb = employeeService.findByWopNumber(employee.getWopNumber());
    if ( employeeDb != null ) {
      ObjectError error = new ObjectError("employee",
                                          "There is employee on same wop number . <br> System message -->");
      result.addError(error);
    }

    if ( result.hasErrors() ) {
      model.addAttribute("addStatus", true);
      model.addAttribute("employee", employee);
      return commonThings(model);
    }

    employee.setMobileOne(commonService.commonMobileNumberLengthValidator(employee.getMobileOne()));
    employee.setMobileTwo(commonService.commonMobileNumberLengthValidator(employee.getMobileTwo()));
    employee.setLand(commonService.commonMobileNumberLengthValidator(employee.getLand()));

    if ( employee.getId() == null ) {
      Employee lastEmployee = employeeService.lastEmployee();
      if ( lastEmployee.getCode() == null ) {
        employee.setCode("SLPE" + makeAutoGenerateNumberService.numberAutoGen(null).toString());
      } else {
        employee.setCode("SLPE" + makeAutoGenerateNumberService.numberAutoGen(lastEmployee.getCode().substring(4)).toString());
      }
    }


    //after save employee files and save employee
    Employee employeeSaved = employeeService.persist(employee);
    //if employee state is not working he or she cannot access to the system
    if ( !employee.getEmployeeStatus().equals(EmployeeStatus.WORKING) ) {
      User user = userService.findUserByEmployee(employeeService.findByNic(employee.getNic()));
      //if employee not a user
      if ( user != null ) {
        user.setEnabled(false);
        userService.persist(user);
      }
    }
// save employee institute
    if ( employee.getId() == null ) {
      EmployeeInstitute employeeInstitute = new EmployeeInstitute();
      employeeInstitute.setEmployee(employeeSaved);
      employeeInstitute.setInstitute(employee.getInstitute());
      employeeInstitute.setStartAt(employee.getDateOfAssignment());
      employeeInstitute.setInstituteChangeReason(InstituteChangeReason.IMPORTANCEOFSERVICE);
      employeeInstituteService.persist(employeeInstitute);
    }

    try {
      //save employee images file
      if ( employee.getFile().getOriginalFilename() != null ) {
        EmployeeFiles employeeFiles = employeeFilesService.findByEmployee(employeeSaved);
        if ( employeeFiles != null ) {
          // update new contents
          employeeFiles.setPic(employee.getFile().getBytes());
          // Save all to database
        } else {
          employeeFiles = new EmployeeFiles(employee.getFile().getOriginalFilename(),
                                            employee.getFile().getContentType(),
                                            employee.getFile().getBytes(),
                                            employee.getNic().concat("-" + LocalDateTime.now()),
                                            UUID.randomUUID().toString().concat("employee"));
          employeeFiles.setEmployee(employee);
        }
        employeeFilesService.persist(employeeFiles);
      }
      employee = employeeSaved;
      return "redirect:/employee";

    } catch ( Exception e ) {
      ObjectError error = new ObjectError("employee",
                                          "There is already in the system. <br>System message -->" + e.toString());
      result.addError(error);
      if ( employee.getId() != null ) {
        model.addAttribute("addStatus", true);
        System.out.println("id is null");
      } else {
        model.addAttribute("addStatus", false);
      }
      model.addAttribute("employee", employee);
      return commonThings(model);
    }

  }

  @GetMapping( value = "/remove/{id}" )
  public String removeEmployee(@PathVariable Integer id) {
    employeeService.delete(id);
    return "redirect:/employee";
  }

  //To search employee any giving employee parameter
  @GetMapping( value = "/search" )
  public String search(Model model, Employee employee) {
    model.addAttribute("employeeDetail", employeeService.search(employee));
    return "employee/employee-detail";
  }

  @GetMapping( value = "/findAll" )
  public String findAllForm(Model model) {
    model.addAttribute("employee", new Employee());
    return "employee/findEmployee";
  }

  @PostMapping( value = "/findAll" )
  public String findAll(@ModelAttribute( "employee" ) Employee employee, Model model) {

    List< Employee > employees = employeeService.search(employee);

    if ( employees == null ) {
      model.addAttribute("employeeNotFound", "There is not employee in the system according to the provided details" +
          " or that employee already be a user in the system" +
          " \n Could you please search again !!");
    } else {
      model.addAttribute("employees", employees);
    }

    model.addAttribute("employee", new Employee());
    return "employee/findEmployee";
  }

  @GetMapping( value = "/supervisor/{id}" )
  @ResponseBody
  public MappingJacksonValue findSupervisor(@PathVariable Integer id) {
    Institute institute = instituteService.findById(id);

    MappingJacksonValue mappingJacksonValue;
    if ( institute != null ) {
      List< Employee > employees = employeeService.findByInstitute(institute);
      if ( employees.isEmpty() ) {
        mappingJacksonValue = new MappingJacksonValue(employeeService.findAll());
      } else {
        mappingJacksonValue = new MappingJacksonValue(employees);
      }
    } else {
      mappingJacksonValue = new MappingJacksonValue(employeeService.findAll());
    }

    SimpleBeanPropertyFilter simpleBeanPropertyFilterOne = SimpleBeanPropertyFilter
        .filterOutAllExcept("id", "name");

    FilterProvider filters = new SimpleFilterProvider()
        .addFilter("Employee", simpleBeanPropertyFilterOne);
    mappingJacksonValue.setFilters(filters);
    return mappingJacksonValue;
  }

}
