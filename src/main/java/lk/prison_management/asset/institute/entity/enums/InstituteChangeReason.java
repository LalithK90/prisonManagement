package lk.prison_management.asset.institute.entity.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InstituteChangeReason {
  IMPORTANCEOFSERVICE("Importance of Service"),
  EMLOYEEREQUEST("Employee Request"),
  DISIPILINARYACTION("Disciplinary Action"),
  ANNURALTRANSFER("Annual Transfer");

  private final String workingPlaceChangeReason;

}

