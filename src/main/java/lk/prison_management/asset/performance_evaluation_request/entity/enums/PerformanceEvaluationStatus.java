package lk.prison_management.asset.performance_evaluation_request.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PerformanceEvaluationStatus {
  APRS("Appraiser   Signature"),
  APES("Appraisee   Signature"),
  Approved(" Approved");
  private final String performanceEvaluationStatus;

}
