package lk.prison_management.asset.censure_file.service;

import lk.prison_management.asset.censure.controller.CensureController;
import lk.prison_management.asset.censure.entitiy.Censure;
import lk.prison_management.asset.censure_file.dao.CensureFilesDao;
import lk.prison_management.asset.censure_file.entity.CensureFiles;
import lk.prison_management.asset.common_asset.model.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;

@Service
@CacheConfig(cacheNames = "commendationFiles")
public class CensureFilesService {
    private final CensureFilesDao censureFilesDao;

    @Autowired
    public CensureFilesService(CensureFilesDao censureFilesDao) {
        this.censureFilesDao = censureFilesDao;
    }

    public CensureFiles findByName(String filename) {
        return censureFilesDao.findByName(filename);
    }

    public void persist(CensureFiles storedFile) {
        censureFilesDao.save(storedFile);
    }


    public List< CensureFiles > search(CensureFiles censureFiles) {
        ExampleMatcher matcher = ExampleMatcher
            .matching()
            .withIgnoreCase()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< CensureFiles > employeeFilesExample = Example.of(censureFiles, matcher);
        return censureFilesDao.findAll(employeeFilesExample);
    }

    public CensureFiles findById(Integer id) {
        return censureFilesDao.getOne(id);
    }

    public CensureFiles findByNewID(String filename) {
        return censureFilesDao.findByNewId(filename);
    }


    public FileInfo censureFileDownloadLinks(Censure censure) {
        CensureFiles censureFiles = censureFilesDao.findByCensure(censure);
        if (censureFiles != null) {
            String filename = censureFiles.getName();
            String url = MvcUriComponentsBuilder
                .fromMethodName(CensureController.class, "downloadFile", censureFiles.getNewId())
                .build()
                .toString();
            return new FileInfo(filename, censureFiles.getCreatedAt(), url);
        }
        return null;
    }

    public CensureFiles findByEmployee(Censure censure) {
        return censureFilesDao.findByCensure(censure);
    }
}
