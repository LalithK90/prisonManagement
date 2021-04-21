package lk.prison_management.asset.process_management.mode;


import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.performance_evaluation_approval.entity.PerformanceEvaluationApproval;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeePerformanceAnalysis {
    private PerformanceEvaluationApproval performanceEvaluationApproval;
    private List <Employee>employees;

}
