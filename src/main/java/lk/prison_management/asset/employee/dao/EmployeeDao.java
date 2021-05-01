package lk.prison_management.asset.employee.dao;


import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.institute.entity.Institute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface EmployeeDao extends JpaRepository< Employee, Integer > {
  Employee findFirstByOrderByIdDesc();

  Employee findByNic(String nic);

  Employee findByWopNumber(String wopNumber);
  //list eke last in first show for clerk or cc
  List<Employee> findByInstituteOrderByIdDesc(Institute institute);


  List<Employee> findByInstituteOrderByIdDesc(Institute institute);

  List< Employee > findBySupervisor(Employee employee);
  List< Employee > findByCreatedAtBetween(LocalDateTime form, LocalDateTime to);

//list eke last in first show for super user
  List<Employee> findAllByOrderByIdDesc();
}
