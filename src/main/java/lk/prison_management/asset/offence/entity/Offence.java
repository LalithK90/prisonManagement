package lk.prison_management.asset.offence.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.prison_management.asset.censure.entitiy.Censure;
import lk.prison_management.asset.commendation.entity.Commendation;
import lk.prison_management.asset.offence.entity.enums.OffenceType;
import lk.prison_management.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("Offence")
public class Offence extends AuditEntity {

    @Column(length = 800)
    private String name;

    @Enumerated(EnumType.STRING)
    private OffenceType offenceType;


    @OneToMany(mappedBy = "offence")
    private List< Censure > censures;
}
