package lk.prison_management.asset.qualification.dao;


import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.qualification.entity.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QualificationDao extends JpaRepository< Qualification, Integer> {
  List< Qualification> findByEmployee(Employee employee);


}
