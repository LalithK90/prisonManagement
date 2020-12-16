package lk.prison_management.asset.employee_qualification.dao;

import lk.prison_management.asset.employee_qualification.entity.EmployeeQualification;
import lk.prison_management.asset.institute.entity.Institute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeQualificationDao extends JpaRepository< EmployeeQualification, Integer> {
}
