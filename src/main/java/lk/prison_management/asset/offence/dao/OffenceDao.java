package lk.prison_management.asset.offence.dao;

import lk.prison_management.asset.offence.entity.Offence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffenceDao extends JpaRepository<Offence, Integer> {
}
