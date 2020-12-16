package lk.prison_management.asset.leave.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.institute.entity.Institute;
import lk.prison_management.asset.leave.entity.enums.LeaveType;
import lk.prison_management.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "Leave" )
public class Leave extends AuditEntity {

  @Column( nullable = false )
  private LocalDateTime startAt, endAt;

  private String dayCount, hourCount;

  @Enumerated( EnumType.STRING)
  private LeaveType leaveType;

  @ManyToOne
  private Employee employee;

  @ManyToOne
  private Institute institute;
}
