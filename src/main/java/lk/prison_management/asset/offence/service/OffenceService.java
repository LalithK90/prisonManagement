package lk.prison_management.asset.offence.service;

import lk.prison_management.asset.offence.dao.OffenceDao;
import lk.prison_management.asset.offence.entity.Offence;
import lk.prison_management.util.interfaces.AbstractService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OffenceService implements AbstractService<Offence, Integer> {
    private final OffenceDao offenceDao;

    public OffenceService(OffenceDao offenceDao) {
        this.offenceDao = offenceDao;
    }

    public List<Offence> findAll() {
        return offenceDao.findAll();
    }

    public Offence findById(Integer id) {
        return offenceDao.getOne(id);
    }

    public Offence persist(Offence offence) {
        return offenceDao.save(offence);
    }

    public boolean delete(Integer id) {
        offenceDao.deleteById(id);
        return true;
    }

    public List<Offence> search(Offence offence) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Offence> instituteExample = Example.of(offence, matcher);
        return offenceDao.findAll(instituteExample);
    }
}
