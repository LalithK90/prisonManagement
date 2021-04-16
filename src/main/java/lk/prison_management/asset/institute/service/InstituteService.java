package lk.prison_management.asset.institute.service;

import lk.prison_management.asset.common_asset.model.enums.LiveOrDead;
import lk.prison_management.asset.institute.dao.InstituteDao;
import lk.prison_management.asset.institute.entity.Institute;
import lk.prison_management.util.interfaces.AbstractService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstituteService implements AbstractService<Institute, Integer> {
    private final InstituteDao instituteDao;

    public InstituteService(InstituteDao instituteDao) {
        this.instituteDao = instituteDao;
    }

    public List<Institute> findAll() {
        //.stream thamai delete wena eka nathi wela penne institute eke.
        return instituteDao.findAll() .stream().filter(x->x.getLiveOrDead().equals(LiveOrDead.ACTIVE)).collect(Collectors.toList());
    }

    public Institute findById(Integer id) {
        return instituteDao.getOne(id);
    }

    public Institute persist(Institute institute) {
if(institute.getId()==null){
    institute.setLiveOrDead(LiveOrDead.ACTIVE);
}
        return instituteDao.save(institute);
    }

    public boolean delete(Integer id) {
      Institute institute = instituteDao.getOne(id);
      institute.setLiveOrDead(LiveOrDead.STOP);
      instituteDao.save(institute);
        return true;
    }

    public List<Institute> search(Institute institute) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Institute> instituteExample = Example.of(institute, matcher);
        return instituteDao.findAll(instituteExample);
    }
}
