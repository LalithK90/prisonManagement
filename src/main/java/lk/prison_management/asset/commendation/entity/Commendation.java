package lk.prison_management.asset.commendation.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.prison_management.asset.commendation.entity.enums.OffenceType;
import lk.prison_management.util.audit.AuditEntity;
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
@JsonFilter("Commendation")
public class Commendation extends AuditEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private OffenceType offenceType;
}
