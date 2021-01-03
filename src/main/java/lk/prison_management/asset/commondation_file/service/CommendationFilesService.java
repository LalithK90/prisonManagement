package lk.prison_management.asset.commondation_file.service;

import lk.prison_management.asset.commendation.controller.CommendationController;
import lk.prison_management.asset.commendation.entity.Commendation;
import lk.prison_management.asset.common_asset.model.FileInfo;
import lk.prison_management.asset.commondation_file.dao.CommendationFilesDao;
import lk.prison_management.asset.commondation_file.entity.CommendationFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;

@Service
@CacheConfig(cacheNames = "commendationFiles")
public class CommendationFilesService {
    private final CommendationFilesDao commendationFilesDao;

    @Autowired
    public CommendationFilesService(CommendationFilesDao commendationFilesDao) {
        this.commendationFilesDao = commendationFilesDao;
    }

    public CommendationFiles findByName(String filename) {
        return commendationFilesDao.findByName(filename);
    }

    public void persist(CommendationFiles storedFile) {
        commendationFilesDao.save(storedFile);
    }


    public List<CommendationFiles> search(CommendationFiles commendationFiles) {
        ExampleMatcher matcher = ExampleMatcher
            .matching()
            .withIgnoreCase()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< CommendationFiles > employeeFilesExample = Example.of(commendationFiles, matcher);
        return commendationFilesDao.findAll(employeeFilesExample);
    }

    public CommendationFiles findById(Integer id) {
        return commendationFilesDao.getOne(id);
    }

    public CommendationFiles findByNewID(String filename) {
        return commendationFilesDao.findByNewId(filename);
    }

    @Cacheable
    public FileInfo employeeFileDownloadLinks(Commendation commendation) {
        CommendationFiles commendationFiles = commendationFilesDao.findByCommendation(commendation);
        if (commendationFiles != null) {
            String filename = commendationFiles.getName();
            String url = MvcUriComponentsBuilder
                .fromMethodName(CommendationController.class, "downloadFile", commendationFiles.getNewId())
                .build()
                .toString();
            return new FileInfo(filename, commendationFiles.getCreatedAt(), url);
        }
        return null;
    }

    public CommendationFiles findByCommendation(Commendation commendation) {
        return commendationFilesDao.findByCommendation(commendation);
    }
}
