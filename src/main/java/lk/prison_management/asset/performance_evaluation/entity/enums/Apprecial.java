package lk.prison_management.asset.performance_evaluation.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Apprecial {
  EX("Excellent"),
  AA("Above Average"),
  A("Average"),
  BA("Below Average");
  private final String apprecial;
}
