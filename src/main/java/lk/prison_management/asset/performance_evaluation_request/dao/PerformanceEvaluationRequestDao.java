package lk.prison_management.asset.performance_evaluation_request.dao;

import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.performance_evaluation_request.entity.PerformanceEvaluationRequest;
import lk.prison_management.asset.performance_evaluation_request.entity.enums.PerformanceEvaluationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PerformanceEvaluationRequestDao extends JpaRepository< PerformanceEvaluationRequest, Integer> {
  List< PerformanceEvaluationRequest > findByEmployee(Employee employee);

  PerformanceEvaluationRequest findByEmployeeAndPerformanceEvaluationStatus(Employee employee, PerformanceEvaluationStatus performanceEvaluationStatus);

  PerformanceEvaluationRequest findByEmployeeAndFormDateAndToDate(Employee employee, LocalDate startDate, LocalDate endDate);
}
