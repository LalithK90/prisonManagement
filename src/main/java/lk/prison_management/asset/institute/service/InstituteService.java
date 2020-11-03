package lk.prison_management.asset.institute.service;

import lk.prison_management.asset.institute.dao.InstituteDao;
import lk.prison_management.asset.institute.entity.Institute;
import lk.prison_management.util.interfaces.AbstractService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstituteService implements AbstractService<Institute, Integer> {
    private final InstituteDao instituteDao;

    public InstituteService(InstituteDao instituteDao) {
        this.instituteDao = instituteDao;
    }

    public List<Institute> findAll() {
        return instituteDao.findAll();
    }

    public Institute findById(Integer id) {
        return instituteDao.getOne(id);
    }

    public Institute persist(Institute institute) {
        return instituteDao.save(institute);
    }

    public boolean delete(Integer id) {
        instituteDao.deleteById(id);
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
