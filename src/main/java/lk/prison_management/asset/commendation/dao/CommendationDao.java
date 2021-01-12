package lk.prison_management.asset.commendation.dao;

import lk.prison_management.asset.commendation.entity.Commendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommendationDao extends JpaRepository< Commendation, Integer> {
  Commendation findFirstByOrderByIdDesc();
}
