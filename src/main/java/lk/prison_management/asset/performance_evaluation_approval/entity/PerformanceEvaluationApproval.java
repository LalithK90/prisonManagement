package lk.prison_management.asset.performance_evaluation_approval.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.performance_evaluation_request.entity.PerformanceEvaluationRequest;
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
@JsonFilter( "PerformanceEvaluationApproval" )
public class PerformanceEvaluationApproval extends AuditEntity {


  //II.Part filled  Appraiser
//1. Comment by appraiser on the Appraisee performance:
//      1.	Attitude: -//
  @Enumerated( EnumType.STRING )
  private Apprecial attitude;
  //      1.1.	Knowledge in the Area / Field of work: -
  @Enumerated( EnumType.STRING )
  private Apprecial knowledge;

  //      1.2.	Ability to work under pressure: -
  @Enumerated( EnumType.STRING )
  private Apprecial ability;
  //      1.3.	Adaptability / Flexibility: -
  @Enumerated( EnumType.STRING )
  private Apprecial adaptabilityFlexibility;
  //      1.4.	Innovation: -
  @Enumerated( EnumType.STRING )
  private Apprecial innovation;
  //      1.5.	Creativity: -
  @Enumerated( EnumType.STRING )
  private Apprecial creativity;
  //      2.	Computer Skills: -
  @Enumerated( EnumType.STRING )
  private Apprecial skills;
  //      2.1.	Working with subordinates: -
  @Enumerated( EnumType.STRING )
  private Apprecial subordinates;
  //      2.2.	Working with superiors: -
  @Enumerated( EnumType.STRING )
  private Apprecial superiors;
  //      3.	Working with colleagues: -
  @Enumerated( EnumType.STRING )
  private Apprecial colleagues;
  //      3.1.	Working with General Public / Beneficiaries / Customers / Suppliers: -
  @Enumerated( EnumType.STRING )
  private Apprecial publicBeneficiariesCustomersSuppliers;
  //      3.1.	Negotiation / Persuasion Skills: -
  @Enumerated( EnumType.STRING )
  private Apprecial negotiationPersuasionSkills;
  //      3.2.	Project Planning, Implementation & Control: -
  @Enumerated( EnumType.STRING )
  private Apprecial projectPlanningImplementationAndControl;
  //      3.3.	Decision Making / Problem Solving: -
  @Enumerated( EnumType.STRING )
  private Apprecial decisionMakingProblemSolving;
  //      3.4.	Creating / Maintaining Standards: -
  @Enumerated( EnumType.STRING )
  private Apprecial creatingMaintainingStandards;
  //      4.	Time Management: -
  @Enumerated( EnumType.STRING )
  private Apprecial timeManagement;
  //      4.1.	Listening: -
  @Enumerated( EnumType.STRING )
  private Apprecial listening;
  //      4.2.	Written Communication: -
  @Enumerated( EnumType.STRING )
  private Apprecial writtenCommunication;
  //      4.3.	Verbal Communication: -
  @Enumerated( EnumType.STRING )
  private Apprecial verbalCommunication;
  //      4.4.	Presentation: -
  @Enumerated( EnumType.STRING )
  private Apprecial presentation;
  //      21.	Handling of Power and Managing Grievances: -
  @Enumerated( EnumType.STRING )
  private Apprecial powerAndManagingGrievances;
  //      22.	Risk taking: -
  @Enumerated( EnumType.STRING )
  private Apprecial riskTaking;
  //      23.	Strategic Planning: -
  @Enumerated( EnumType.STRING )
  private Apprecial strategicPlanning;
  //      24.	Delegating: -
  @Enumerated( EnumType.STRING )
  private Apprecial delegating;

  //  Overall apprecial : Excellent , Above Average , Average, Below Average
  @Enumerated( EnumType.STRING )
  private Apprecial overallApprecial;

  @OneToOne(cascade = CascadeType.ALL)
  @MapsId
  @JoinColumn(name = "performanceEvaluationRequest_id")
  private PerformanceEvaluationRequest performanceEvaluationRequest;

}
