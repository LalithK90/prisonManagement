package lk.prison_management.asset.leave.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LeaveType {
  WI("Work Indipedent"),
  M("Medical"),
  C("Casual");

  private final String leaveType;
}
