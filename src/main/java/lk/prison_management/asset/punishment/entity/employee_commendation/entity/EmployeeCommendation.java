package lk.prison_management.asset.punishment.entity.employee_commendation.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.prison_management.asset.commendation.entity.Commendation;
import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "Commendation" )
public class EmployeeCommendation extends AuditEntity {

  @ManyToOne
  private Employee employee;

  @ManyToOne
  private Commendation commendation;
}
