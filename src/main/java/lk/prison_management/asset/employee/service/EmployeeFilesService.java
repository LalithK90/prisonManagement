package lk.prison_management.asset.employee.service;


import lk.prison_management.asset.common_asset.model.FileInfo;
import lk.prison_management.asset.employee.controller.EmployeeController;
import lk.prison_management.asset.employee.dao.EmployeeFilesDao;
import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.employee.entity.EmployeeFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.stream.Collectors;
import java.util.*;

@Service
@CacheConfig( cacheNames = "employeeFiles" )
public class EmployeeFilesService {
    private final EmployeeFilesDao employeeFilesDao;

    @Autowired
    public EmployeeFilesService(EmployeeFilesDao employeeFilesDao) {
        this.employeeFilesDao = employeeFilesDao;
    }

    public EmployeeFiles findByName(String filename) {
        return employeeFilesDao.findByName(filename);
    }

    public void persist(EmployeeFiles storedFile) {
        employeeFilesDao.save(storedFile);
    }


    public List< EmployeeFiles > search(EmployeeFiles employeeFiles) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< EmployeeFiles > employeeFilesExample = Example.of(employeeFiles, matcher);
        return employeeFilesDao.findAll(employeeFilesExample);
    }

    public EmployeeFiles findById(Integer id) {
        return employeeFilesDao.getOne(id);
    }

    public EmployeeFiles findByEmployee(Employee employee) {
        return employeeFilesDao.findByEmployee(employee);
    }

    public EmployeeFiles findByNewID(String filename) {
        return employeeFilesDao.findByNewId(filename);
    }

    @Cacheable

    public FileInfo employeeFileDownloadLinks(Employee employee) {
        EmployeeFiles userDetailsFiles = employeeFilesDao.findByEmployee(employee);
        if (userDetailsFiles != null) {
            String filename = userDetailsFiles.getName();
            String url = MvcUriComponentsBuilder
                .fromMethodName(EmployeeController.class, "downloadFile", userDetailsFiles.getNewId())
                .build()
                .toString();
            return new FileInfo(filename, userDetailsFiles.getCreatedAt(), url);
        }
        return null;
    }

}
