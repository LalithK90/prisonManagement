package lk.prison_management.asset.qualification.dao;


import lk.prison_management.asset.qualification.entity.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QualificationDao extends JpaRepository< Qualification, Integer> {

/*//select * from district where province = ?1
    List<Qualification> findByProvince(Province province);*/

}
