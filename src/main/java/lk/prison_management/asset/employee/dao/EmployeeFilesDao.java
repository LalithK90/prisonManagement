package lk.prison_management.asset.employee.dao;

import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.employee.entity.EmployeeFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeFilesDao extends JpaRepository< EmployeeFiles, Integer > {
    EmployeeFiles  findByEmployee(Employee employee);

    EmployeeFiles findByName(String filename);

    EmployeeFiles findByNewName(String filename);

    EmployeeFiles findByNewId(String filename);
}
