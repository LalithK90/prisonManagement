package lk.prison_management.asset.performance_evaluation.dao;

import lk.prison_management.asset.performance_evaluation.entity.PerformanceEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceEvaluationDao extends JpaRepository< PerformanceEvaluation, Integer> {
}
