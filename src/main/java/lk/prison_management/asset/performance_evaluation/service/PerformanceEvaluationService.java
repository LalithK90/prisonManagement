package lk.prison_management.asset.performance_evaluation.service;


import lk.prison_management.asset.performance_evaluation.dao.PerformanceEvaluationDao;
import lk.prison_management.asset.performance_evaluation.entity.PerformanceEvaluation;
import lk.prison_management.util.interfaces.AbstractService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceEvaluationService implements AbstractService< PerformanceEvaluation, Integer> {
    private final PerformanceEvaluationDao performanceEvaluationDao;

    public PerformanceEvaluationService(PerformanceEvaluationDao performanceEvaluationDao) {
        this.performanceEvaluationDao = performanceEvaluationDao;
    }

    public List<PerformanceEvaluation> findAll() {
        return performanceEvaluationDao.findAll();
    }

    public PerformanceEvaluation findById(Integer id) {
        return performanceEvaluationDao.getOne(id);
    }

    public PerformanceEvaluation persist(PerformanceEvaluation performanceEvaluationResult) {
        return performanceEvaluationDao.save(performanceEvaluationResult);
    }

    public boolean delete(Integer id) {
        performanceEvaluationDao.deleteById(id);
        return true;
    }

    public List<PerformanceEvaluation> search(PerformanceEvaluation performanceEvaluationResult) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<PerformanceEvaluation> instituteExample = Example.of(performanceEvaluationResult, matcher);
        return performanceEvaluationDao.findAll(instituteExample);
    }
}
