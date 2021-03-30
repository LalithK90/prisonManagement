package lk.prison_management.asset.commendation.dao;

import lk.prison_management.asset.commendation.entity.Commendation;
import lk.prison_management.asset.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommendationDao extends JpaRepository< Commendation, Integer> {
  Commendation findFirstByOrderByIdDesc();

  List< Commendation> findByEmployee(Employee employee);
}
