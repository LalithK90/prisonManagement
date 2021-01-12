package lk.prison_management.asset.commendation.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.prison_management.asset.common_asset.model.FileInfo;
import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "Commendation" )
public class Commendation extends AuditEntity {

  private String description;

  private String refNumber;

  @ManyToOne
  private Employee employee;

  @Transient
  private MultipartFile file;

  @Transient
  private FileInfo fileInfo;


}
