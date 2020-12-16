package lk.prison_management.asset.employee_qualification.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.prison_management.asset.employee_qualification.entity.enums.EmployeeQualificationStatus;
import lk.prison_management.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "EmployeeQualification" )
public class EmployeeQualification extends AuditEntity {

  @Enumerated( EnumType.STRING)
  private EmployeeQualificationStatus employeeQualificationStatus;

  @Column( nullable = false )
  private LocalDateTime durationStartAt;

  @Column( nullable = false )
  private LocalDateTime  durationEndAt;
}
