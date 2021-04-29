package lk.prison_management.asset.performance_evaluation_request.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Responsibility {

  ORP(" Organizing Religious Programs"),
  OHP(" Organizing Health Programs"),
  OMHP(" Organizing Mental Health Programs"),
  OEP(" Organizing Education Programs"),
  OVP(" Organizing Vocational Programs"),
  OLAP(" Organizing Legal Aids Programs");

  private final String responsibility;
}
