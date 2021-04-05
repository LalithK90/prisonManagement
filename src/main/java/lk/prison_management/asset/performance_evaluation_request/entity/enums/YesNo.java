package lk.prison_management.asset.performance_evaluation_request.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum YesNo {
  Y("Yes"),
  N("No");
  private final String yesNo;
}
