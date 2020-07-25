package lk.prisonManagement.asset.punishment.service;

import lk.prisonManagement.asset.punishment.dao.PunishmentDao;
import lk.prisonManagement.asset.punishment.entity.Punishment;
import lk.prisonManagement.util.interfaces.AbstractService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PunishmentService implements AbstractService<Punishment, Integer> {
    private final PunishmentDao offenceDao;

    public PunishmentService(PunishmentDao offenceDao) {
        this.offenceDao = offenceDao;
    }

    public List<Punishment> findAll() {
        return offenceDao.findAll();
    }

    public Punishment findById(Integer id) {
        return offenceDao.getOne(id);
    }

    public Punishment persist(Punishment punishment) {
        return offenceDao.save(punishment);
    }

    public boolean delete(Integer id) {
        offenceDao.deleteById(id);
        return true;
    }

    public List<Punishment> search(Punishment punishment) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Punishment> instituteExample = Example.of(punishment, matcher);
        return offenceDao.findAll(instituteExample);
    }
}
