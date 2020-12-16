package lk.prison_management.asset.commendation.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.prison_management.asset.commendation.entity.enums.OffenceType;
import lk.prison_management.asset.offence.entity.Offence;
import lk.prison_management.asset.punishment.entity.Punishment;
import lk.prison_management.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "Commendation" )
public class Commendation extends AuditEntity {

  private String name, remarks;

  @Column(nullable = false)
  private LocalDate incidentDate;

  @ManyToOne
  private Offence offence;

  @ManyToOne
  private Punishment punishment;
}
