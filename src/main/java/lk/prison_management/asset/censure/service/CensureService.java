package lk.prison_management.asset.censure.service;

import lk.prison_management.asset.censure.dao.CensureDao;
import lk.prison_management.asset.censure.entitiy.Censure;
import lk.prison_management.util.interfaces.AbstractService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CensureService implements AbstractService< Censure, Integer> {
    private final CensureDao censureDao;

    public CensureService(CensureDao censureDao) {
        this.censureDao = censureDao;
    }

    public List< Censure > findAll() {
        return censureDao.findAll();
    }

    public Censure findById(Integer id) {
        return censureDao.getOne(id);
    }

    public Censure persist(Censure censure) {
        return censureDao.save(censure);
    }

    public boolean delete(Integer id) {
        censureDao.deleteById(id);
        return true;
    }

    public List< Censure > search(Censure censure) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< Censure > instituteExample = Example.of(censure, matcher);
        return censureDao.findAll(instituteExample);
    }
}
