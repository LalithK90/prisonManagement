package lk.prison_management.asset.employee_institute.dao;

import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.employee_institute.entity.EmployeeInstitute;
import lk.prison_management.asset.institute.entity.Institute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeInstituteDao extends JpaRepository< EmployeeInstitute, Integer> {
  List< EmployeeInstitute> findByEmployee(Employee employee);
}
