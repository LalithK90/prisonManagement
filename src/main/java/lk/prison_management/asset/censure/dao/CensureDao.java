package lk.prison_management.asset.censure.dao;

import lk.prison_management.asset.censure.entitiy.Censure;
import lk.prison_management.asset.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CensureDao extends JpaRepository< Censure, Integer> {
  List< Censure> findByEmployee(Employee employee);
}
