package lk.prisonManagement.asset.punishment.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.prisonManagement.asset.punishment.entity.Enum.PunishmentType;
import lk.prisonManagement.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("Offence")
public class Punishment extends AuditEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private PunishmentType punishmentType;
}
