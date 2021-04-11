package lk.prison_management.asset.punishment.dao;

import lk.prison_management.asset.punishment.entity.Punishment;
import lk.prison_management.asset.punishment.entity.enums.PunishmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PunishmentDao extends JpaRepository<Punishment, Integer> {
  List< Punishment> findByPunishmentType(PunishmentType punishmentType);
}
