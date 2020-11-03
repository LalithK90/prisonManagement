package lk.prison_management.asset.commendation.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OffenceType {
    FSO("First Schedule of Offences"),
    SSO("Second  Schedule of Offences"),
    SD("Summary Disciplinary");

    private final String offenceType;

}
