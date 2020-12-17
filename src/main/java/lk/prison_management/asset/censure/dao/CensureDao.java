package lk.prison_management.asset.censure.dao;

import lk.prison_management.asset.censure.entitiy.Censure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CensureDao extends JpaRepository< Censure, Integer> {
}
