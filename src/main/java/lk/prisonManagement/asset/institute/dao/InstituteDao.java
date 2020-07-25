package lk.prisonManagement.asset.institute.dao;

import lk.prisonManagement.asset.institute.entity.Institute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstituteDao extends JpaRepository<Institute, Integer> {
}
