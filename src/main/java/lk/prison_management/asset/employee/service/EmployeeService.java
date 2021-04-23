package lk.prison_management.asset.employee.service;


import lk.prison_management.asset.common_asset.model.enums.LiveOrDead;
import lk.prison_management.asset.employee.dao.EmployeeDao;
import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.employee.entity.enums.EmployeeStatus;
import lk.prison_management.asset.institute.entity.Institute;
import lk.prison_management.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
// spring transactional annotation need to tell spring to this method work through the project
@CacheConfig( cacheNames = "employee" )
public class EmployeeService implements AbstractService< Employee, Integer > {

  private final EmployeeDao employeeDao;

  @Autowired
  public EmployeeService(EmployeeDao employeeDao) {
    this.employeeDao = employeeDao;
  }

  @Cacheable
  public List< Employee > findAll() {
    return employeeDao.findAll().stream().filter(x -> x.getLiveOrDead().equals(LiveOrDead.ACTIVE)).collect(Collectors.toList());
  }

  @Cacheable
  public Employee findById(Integer id) {
    return employeeDao.getOne(id);
  }

  @Caching( evict = {@CacheEvict( value = "employee", allEntries = true )},
      put = {@CachePut( value = "employee", key = "#employee.id" )} )
  // Data save kranwa
  @Transactional
  public Employee persist(Employee employee) {
    if ( employee.getId() == null ) {
      employee.setLiveOrDead(LiveOrDead.ACTIVE);
      //ethakota aluth ekak nam ea employee ge status eka employee status eka working kiyala hadanawa
      employee.setEmployeeStatus(EmployeeStatus.WORKING);
    }
    return employeeDao.save(employee);
  }

  public boolean delete(Integer id) {
    Employee employee = employeeDao.getOne(id);
    employee.setLiveOrDead(LiveOrDead.STOP);
    employeeDao.save(employee);
    return false;
  }

  @Cacheable
  public List< Employee > search(Employee employee) {
    ExampleMatcher matcher = ExampleMatcher
        .matching()
        .withIgnoreCase()
        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    Example< Employee > employeeExample = Example.of(employee, matcher);
    return employeeDao.findAll(employeeExample);
  }

  public boolean isEmployeePresent(Employee employee) {
    return employeeDao.equals(employee);
  }


  public Employee lastEmployee() {
    return employeeDao.findFirstByOrderByIdDesc();
  }

  @Cacheable
  public Employee findByNic(String nic) {
    return employeeDao.findByNic(nic);
  }

  public Employee findByWopNumber(String wopNumber) {
    return employeeDao.findByWopNumber(wopNumber);
  }

  public List< Employee > findByInstitute(Institute institute) {
    return employeeDao.findByInstitute(institute);
  }

  public List< Employee > findBySupervisor(Employee employee) {
    return employeeDao.findBySupervisor(employee);
  }

  public List< Employee > findByCreatedAtBetween(LocalDateTime form, LocalDateTime to) {
    return employeeDao.findByCreatedAtBetween(form, to);
  }

}
