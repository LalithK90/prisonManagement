package lk.prison_management.asset.punishment.dao;

import lk.prison_management.asset.punishment.entity.Punishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PunishmentDao extends JpaRepository<Punishment, Integer> {
}
