package lk.prisonManagement.asset.userManagement.dao;

import lk.prisonManagement.asset.userManagement.entity.Enum.UserSessionLogStatus;
import lk.prisonManagement.asset.userManagement.entity.User;
import lk.prisonManagement.asset.userManagement.entity.UserSessionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionLogDao extends JpaRepository<UserSessionLog, Integer > {
    UserSessionLog findByUserAndUserSessionLogStatus(User user, UserSessionLogStatus userSessionLogStatus);
}
