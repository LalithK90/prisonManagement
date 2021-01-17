package lk.prison_management.asset.qualification.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "Qualification" )
public class Qualification extends AuditEntity {

  @Size( min = 2, max = 60, message = "Your name length should be 13" )
  private String name;

  private String certificateNumber;

  @DateTimeFormat( pattern = "yyyy-MM-dd" )
  private LocalDate startDate;

  @DateTimeFormat( pattern = "yyyy-MM-dd" )
  private LocalDate endDate;

  @ManyToOne
  private Employee employee;

}
