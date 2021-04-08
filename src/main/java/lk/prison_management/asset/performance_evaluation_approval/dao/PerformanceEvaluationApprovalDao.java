package lk.prison_management.asset.performance_evaluation_approval.dao;

import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.performance_evaluation_approval.entity.PerformanceEvaluationApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceEvaluationApprovalDao extends JpaRepository< PerformanceEvaluationApproval, Integer> {
}
