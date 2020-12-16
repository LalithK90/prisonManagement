package lk.prison_management.asset.employee_qualification.service;

import lk.prison_management.asset.employee_qualification.dao.EmployeeQualificationDao;
import lk.prison_management.asset.employee_qualification.entity.EmployeeQualification;
import lk.prison_management.util.interfaces.AbstractService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeQualificationService implements AbstractService< EmployeeQualification, Integer> {
    private final EmployeeQualificationDao employeeQualificationDao;

    public EmployeeQualificationService(EmployeeQualificationDao employeeQualificationDao) {
        this.employeeQualificationDao = employeeQualificationDao;
    }

    public List<EmployeeQualification> findAll() {
        return employeeQualificationDao.findAll();
    }

    public EmployeeQualification findById(Integer id) {
        return employeeQualificationDao.getOne(id);
    }

    public EmployeeQualification persist(EmployeeQualification employeeQualification) {
        return employeeQualificationDao.save(employeeQualification);
    }

    public boolean delete(Integer id) {
        employeeQualificationDao.deleteById(id);
        return true;
    }

    public List<EmployeeQualification> search(EmployeeQualification employeeQualification) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<EmployeeQualification> employeeQualificationExample = Example.of(employeeQualification, matcher);
        return employeeQualificationDao.findAll(employeeQualificationExample);
    }
}
