package lk.prison_management.asset.employee_leave.dao;

import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.employee_leave.entity.EmployeeLeave;
import lk.prison_management.asset.institute.entity.Institute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeLeaveDao extends JpaRepository< EmployeeLeave, Integer> {
  List< EmployeeLeave > findByEmployee(Employee employee);
}
