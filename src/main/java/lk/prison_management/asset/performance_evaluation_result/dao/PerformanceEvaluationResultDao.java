package lk.prison_management.asset.performance_evaluation_result.dao;

import lk.prison_management.asset.performance_evaluation_result.entity.PerformanceEvaluationResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceEvaluationResultDao extends JpaRepository< PerformanceEvaluationResult, Integer> {
}
