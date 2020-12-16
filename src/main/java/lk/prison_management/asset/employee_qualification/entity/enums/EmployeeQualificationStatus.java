package lk.prison_management.asset.employee_qualification.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmployeeQualificationStatus {
  EX("Excellent"),
  MD("Mediam"),
  AV("Avarange"),
  WE("Week");

  private final String employeeQualificationStatus;
}
