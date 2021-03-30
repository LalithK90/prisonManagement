package lk.prison_management.asset.employee_leave.service;


import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.employee_leave.dao.EmployeeLeaveDao;
import lk.prison_management.asset.employee_leave.entity.EmployeeLeave;
import lk.prison_management.util.interfaces.AbstractService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeLeaveService implements AbstractService< EmployeeLeave, Integer> {
    private final EmployeeLeaveDao employeeLeaveDao;

    public EmployeeLeaveService(EmployeeLeaveDao employeeLeaveDao) {
        this.employeeLeaveDao = employeeLeaveDao;
    }

    public List<EmployeeLeave> findAll() {
        return employeeLeaveDao.findAll();
    }

    public EmployeeLeave findById(Integer id) {
        return employeeLeaveDao.getOne(id);
    }

    public EmployeeLeave persist(EmployeeLeave employeeLeave) {
        return employeeLeaveDao.save(employeeLeave);
    }

    public boolean delete(Integer id) {
        employeeLeaveDao.deleteById(id);
        return true;
    }

    public List<EmployeeLeave> search(EmployeeLeave employeeLeave) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<EmployeeLeave> employeeLeaveExample = Example.of(employeeLeave, matcher);
        return employeeLeaveDao.findAll(employeeLeaveExample);
    }

  public List< EmployeeLeave> findByEmployee(Employee employee) {
  return employeeLeaveDao.findByEmployee(employee);
    }
}
