package lk.prison_management.asset.performance_evaluation_request.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.performance_evaluation_approval.entity.PerformanceEvaluationApproval;
import lk.prison_management.asset.performance_evaluation_request.entity.enums.PerformanceEvaluationStatus;
import lk.prison_management.asset.performance_evaluation_request.entity.enums.Apprecial;
import lk.prison_management.asset.performance_evaluation_request.entity.enums.YesNo;
import lk.prison_management.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "PerformanceEvaluationRequest" )
public class PerformanceEvaluationRequest extends AuditEntity {

  //2. Areas of Responsibility
//2.1Whether you have received a task list : yes /No
  @Enumerated( EnumType.STRING )
  private YesNo taskLists; //(using 'Y' and 'N')
  //2.1Main Areas of Responsibility and Tasks
//2.1.1 Responsibility
  private String responsibility;
  //2.1.2. Tasks
  private String tasks;

  //3. Mid Term Appraisal
//3.1 Are you satisfied about your performance :yes /no
  @Enumerated( EnumType.STRING )
  private YesNo satisfied; //(using 'Y' and 'N')
  //3.2 Any difficulties faced.
  private String difficulties;
  //3.3. Strategies for improvement (if Appraisee is lagging behind):
  private String improvement;
  //      4.Public Relations
//4.1Whether your directly work with general public or other institutes or officers of other sections : yes/no
  @Enumerated( EnumType.STRING )
  private YesNo publicInstitutesOfficers; //(using 'Y' and 'N')
  //4.2Are you satisfied about your work with the general public?
  @Enumerated( EnumType.STRING )
  private YesNo satisfiedGeneralPublic;
  //      4.3 Are there any general public or other officers were unhappy ?
  @Enumerated( EnumType.STRING )
  private YesNo unhappy; //(using 'Y' and 'N')
  //      4.4 if the answer for 4.3 is yes what are the reasons for it?
  private String reasons;


  @Column( nullable = false )
  @DateTimeFormat( pattern = "yyyy-MM-dd" )
  private LocalDate formDate;

  @Column( nullable = false )
  @DateTimeFormat( pattern = "yyyy-MM-dd" )
  private LocalDate toDate;


  @Enumerated( EnumType.STRING )
  private PerformanceEvaluationStatus performanceEvaluationStatus;

  @ManyToOne
  private Employee employee;

  @OneToOne(mappedBy = "performanceEvaluationRequest", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private PerformanceEvaluationApproval performanceEvaluationApproval;

}
