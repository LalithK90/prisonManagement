package lk.prison_management.asset.employee_institute.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.institute.entity.Institute;
import lk.prison_management.asset.institute.entity.enums.InstituteChangeReason;
import lk.prison_management.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "EmployeeInstitute" )
public class EmployeeInstitute extends AuditEntity {

  private String remarks;

  @Column( nullable = false )
  @DateTimeFormat( pattern = "yyyy-MM-dd" )
  private LocalDate startAt;

  @Column( nullable = false )
  @DateTimeFormat( pattern = "yyyy-MM-dd" )
  private LocalDate endAt;

  @Enumerated( EnumType.STRING )
  private InstituteChangeReason instituteChangeReason;

  @ManyToOne
  private Employee employee;

  @ManyToOne
  private Institute institute;

  @Transient
  private Employee supervisor;

}
