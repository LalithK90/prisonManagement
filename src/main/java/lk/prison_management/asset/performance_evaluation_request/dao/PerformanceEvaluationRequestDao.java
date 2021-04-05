package lk.prison_management.asset.performance_evaluation_request.dao;

import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.performance_evaluation_request.entity.PerformanceEvaluationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceEvaluationRequestDao extends JpaRepository< PerformanceEvaluationRequest, Integer> {
  List< PerformanceEvaluationRequest > findByEmployee(Employee employee);
}
