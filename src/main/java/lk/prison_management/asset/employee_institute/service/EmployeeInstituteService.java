package lk.prison_management.asset.employee_institute.service;

import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.employee.service.EmployeeService;
import lk.prison_management.asset.employee_institute.dao.EmployeeInstituteDao;
import lk.prison_management.asset.employee_institute.entity.EmployeeInstitute;
import lk.prison_management.util.interfaces.AbstractService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeInstituteService implements AbstractService< EmployeeInstitute, Integer > {
  private final EmployeeInstituteDao employeeInstituteDao;
  private final EmployeeService employeeService;

  public EmployeeInstituteService(EmployeeInstituteDao employeeInstituteDao, EmployeeService employeeService) {
    this.employeeInstituteDao = employeeInstituteDao;
    this.employeeService = employeeService;
  }

  public List< EmployeeInstitute > findAll() {
    return employeeInstituteDao.findAll();
  }

  public EmployeeInstitute findById(Integer id) {
    return employeeInstituteDao.getOne(id);
  }

  public EmployeeInstitute persist(EmployeeInstitute employeeInstitute) {
    return employeeInstituteDao.save(employeeInstitute);
  }

  public boolean delete(Integer id) {
    employeeInstituteDao.deleteById(id);
    return true;
  }

  public List< EmployeeInstitute > search(EmployeeInstitute employeeInstitute) {
    ExampleMatcher matcher = ExampleMatcher
        .matching()
        .withIgnoreCase()
        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    Example< EmployeeInstitute > employeeInstituteExample = Example.of(employeeInstitute, matcher);
    return employeeInstituteDao.findAll(employeeInstituteExample);
  }

  public List< EmployeeInstitute > findByEmployee(Employee employee) {
    return employeeInstituteDao.findByEmployee(employee);
  }

}
