package lk.prisonManagement.asset.punishment.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PunishmentType {
    FSO("First Schedule of Offences"),
    MIP("Minor Punishments"),
    MAP("Major  Punishments");

    private final String punishmentType;

}
