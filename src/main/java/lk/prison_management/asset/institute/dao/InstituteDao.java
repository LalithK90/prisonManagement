package lk.prison_management.asset.institute.dao;

import lk.prison_management.asset.institute.entity.Institute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstituteDao extends JpaRepository<Institute, Integer> {

}
