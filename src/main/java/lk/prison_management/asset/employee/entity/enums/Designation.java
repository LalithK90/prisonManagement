package lk.prison_management.asset.employee.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Designation {
  CIIIR("Class  III Rehabilitation Officer"),
  CIIR("Class  II Rehabilitation Officer"),
  CIR("Class  I Rehabilitation Officer"),
  CRO("Chief  Rehabilitation Officer"),
  ASPR("Assistant Superintendent of Prison(Rehabilitation)"),
  SPR("Superintendent of Prison(Rehabilitation)"),
  SSPR("Senior  Superintendent of Prison(Rehabilitation)"),
  SLAS("Assistant Commissioner (S.L.A.S)"),
  CR("Commissioner - Rehabilitation");

  private final String designation;
}
