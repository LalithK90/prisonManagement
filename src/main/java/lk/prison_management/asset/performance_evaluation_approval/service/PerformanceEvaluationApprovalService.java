package lk.prison_management.asset.performance_evaluation_approval.service;

import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.performance_evaluation_approval.dao.PerformanceEvaluationApprovalDao;
import lk.prison_management.asset.performance_evaluation_approval.entity.PerformanceEvaluationApproval;
import lk.prison_management.util.interfaces.AbstractService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceEvaluationApprovalService implements AbstractService< PerformanceEvaluationApproval, Integer> {
    private final PerformanceEvaluationApprovalDao performanceEvaluationApprovalDao;

    public PerformanceEvaluationApprovalService(PerformanceEvaluationApprovalDao performanceEvaluationApprovalDao) {
        this.performanceEvaluationApprovalDao = performanceEvaluationApprovalDao;
    }

    public List< PerformanceEvaluationApproval > findAll() {
        return performanceEvaluationApprovalDao.findAll();
    }

    public PerformanceEvaluationApproval findById(Integer id) {
        return performanceEvaluationApprovalDao.getOne(id);
    }

    public PerformanceEvaluationApproval persist(PerformanceEvaluationApproval performanceEvaluationApproval) {
        return performanceEvaluationApprovalDao.save(performanceEvaluationApproval);
    }

    public boolean delete(Integer id) {
        performanceEvaluationApprovalDao.deleteById(id);
        return true;
    }

    public List< PerformanceEvaluationApproval > search(PerformanceEvaluationApproval performanceEvaluationApproval) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< PerformanceEvaluationApproval > instituteExample = Example.of(performanceEvaluationApproval, matcher);
        return performanceEvaluationApprovalDao.findAll(instituteExample);
    }

}
