package lk.prison_management.asset.performance_evaluation_result.service;

import lk.prison_management.asset.performance_evaluation_result.dao.PerformanceEvaluationResultDao;
import lk.prison_management.asset.performance_evaluation_result.entity.PerformanceEvaluationResult;
import lk.prison_management.util.interfaces.AbstractService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceEvaluationResultService implements AbstractService< PerformanceEvaluationResult, Integer> {
    private final PerformanceEvaluationResultDao performanceEvaluationResultDao;

    public PerformanceEvaluationResultService(PerformanceEvaluationResultDao performanceEvaluationResultDao) {
        this.performanceEvaluationResultDao = performanceEvaluationResultDao;
    }

    public List<PerformanceEvaluationResult> findAll() {
        return performanceEvaluationResultDao.findAll();
    }

    public PerformanceEvaluationResult findById(Integer id) {
        return performanceEvaluationResultDao.getOne(id);
    }

    public PerformanceEvaluationResult persist(PerformanceEvaluationResult performanceEvaluationResult) {
        return performanceEvaluationResultDao.save(performanceEvaluationResult);
    }

    public boolean delete(Integer id) {
        performanceEvaluationResultDao.deleteById(id);
        return true;
    }

    public List<PerformanceEvaluationResult> search(PerformanceEvaluationResult performanceEvaluationResult) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<PerformanceEvaluationResult> instituteExample = Example.of(performanceEvaluationResult, matcher);
        return performanceEvaluationResultDao.findAll(instituteExample);
    }
}
