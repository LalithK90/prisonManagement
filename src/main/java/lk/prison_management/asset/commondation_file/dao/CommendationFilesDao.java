package lk.prison_management.asset.commondation_file.dao;

import lk.prison_management.asset.commendation.entity.Commendation;
import lk.prison_management.asset.commondation_file.entity.CommendationFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommendationFilesDao extends JpaRepository< CommendationFiles, Integer > {
    List< CommendationFiles > findByCommendationOrderByIdDesc(Commendation employee);

    CommendationFiles findByName(String filename);

    CommendationFiles findByNewName(String filename);

    CommendationFiles findByNewId(String filename);

    CommendationFiles findByCommendation(Commendation employee);
}
