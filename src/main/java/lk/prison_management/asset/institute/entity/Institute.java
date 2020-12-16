package lk.prison_management.asset.institute.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.prison_management.asset.employee_institute.entity.EmployeeInstitute;
import lk.prison_management.asset.institute.entity.enums.PrisonType;
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
@JsonFilter( "Institute" )
public class Institute extends AuditEntity {

  private String name;

  @Column( unique = true )
  private String land;

  private String address;

  @Column( unique = true )
  private String email;

  @Enumerated( EnumType.STRING )
  private PrisonType prisonType;

  @OneToMany(mappedBy = "institute")
  private List< EmployeeInstitute > employeeInstitutes;
}
