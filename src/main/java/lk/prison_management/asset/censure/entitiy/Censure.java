package lk.prison_management.asset.censure.entitiy;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.prison_management.asset.common_asset.model.FileInfo;
import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.offence.entity.Offence;
import lk.prison_management.asset.punishment.entity.Punishment;
import lk.prison_management.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import java.time.LocalDate;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "Censure" )
public class Censure extends AuditEntity {

  private String refNumber;

  private String description;

  @Column(nullable = false)
  private LocalDate incidentDate;

  @ManyToOne
  private Offence offence;

  @ManyToOne
  private Punishment punishment;

  @ManyToOne
  private Employee employee;

  @Transient
  private MultipartFile file;

  @Transient
  private FileInfo fileInfo;
}
