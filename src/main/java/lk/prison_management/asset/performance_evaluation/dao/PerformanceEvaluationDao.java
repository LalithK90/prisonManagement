package lk.prison_management.asset.performance_evaluation.dao;

import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.performance_evaluation.entity.PerformanceEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceEvaluationDao extends JpaRepository< PerformanceEvaluation, Integer> {
  List< PerformanceEvaluation> findByEmployee(Employee employee);
}
