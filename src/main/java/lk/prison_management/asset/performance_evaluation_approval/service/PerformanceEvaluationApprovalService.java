package lk.prison_management.asset.performance_evaluation_approval.service;

import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.performance_evaluation_request.dao.PerformanceEvaluationRequestDao;
import lk.prison_management.asset.performance_evaluation_request.entity.PerformanceEvaluationRequest;
import lk.prison_management.util.interfaces.AbstractService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceEvaluationApprovalService implements AbstractService< PerformanceEvaluationRequest, Integer> {
    private final PerformanceEvaluationRequestDao performanceEvaluationRequestDao;

    public PerformanceEvaluationApprovalService(PerformanceEvaluationRequestDao performanceEvaluationRequestDao) {
        this.performanceEvaluationRequestDao = performanceEvaluationRequestDao;
    }

    public List< PerformanceEvaluationRequest > findAll() {
        return performanceEvaluationRequestDao.findAll();
    }

    public PerformanceEvaluationRequest findById(Integer id) {
        return performanceEvaluationRequestDao.getOne(id);
    }

    public PerformanceEvaluationRequest persist(PerformanceEvaluationRequest performanceEvaluationRequest) {
        return performanceEvaluationRequestDao.save(performanceEvaluationRequest);
    }

    public boolean delete(Integer id) {
        performanceEvaluationRequestDao.deleteById(id);
        return true;
    }

    public List< PerformanceEvaluationRequest > search(PerformanceEvaluationRequest performanceEvaluationRequest) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< PerformanceEvaluationRequest > instituteExample = Example.of(performanceEvaluationRequest, matcher);
        return performanceEvaluationRequestDao.findAll(instituteExample);
    }

  public List< PerformanceEvaluationRequest > findByEmployee(Employee employee) {
        return performanceEvaluationRequestDao.findByEmployee(employee);
  }
}
