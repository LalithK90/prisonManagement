package lk.prison_management.asset.censure.dao;

import lk.prison_management.asset.censure.entitiy.Censure;
import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.offence.entity.Offence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CensureDao extends JpaRepository< Censure, Integer > {
  List< Censure > findByEmployee(Employee employee);

  List< Censure > findByOffence(Offence offence);

  List< Censure > findByCreatedAtBetweenAndOffence(LocalDateTime form, LocalDateTime to, Offence offence);

  List< Censure> findByCreatedAtIsBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

  Censure findFirstByOrderByIdDesc();
}
