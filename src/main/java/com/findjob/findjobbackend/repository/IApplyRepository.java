package com.findjob.findjobbackend.repository;


import com.findjob.findjobbackend.dto.response.ApplyShowAll;
import com.findjob.findjobbackend.enums.Status;
import com.findjob.findjobbackend.model.Apply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IApplyRepository extends JpaRepository<Apply, Long> {

//    @Query(
//            value = "select a.id as id, a.date as date, a.status as status, a.recruitmentnew_id as recruitmentNewId, a.user_id as userId, " +
//                    "r.title as title, r.vacancies_id as vacanciesId, v.name as vacanciesName, c.avatar as avatar, " +
//                    "u.name as nameUser, u.phone as phoneUser " +
//                    "from apply a " +
//                    "join recruitmentnew r on a.recruitmentnew_id = r.id " +
//                    "join company c on r.company_id = c.id " +
//                    "join city c2 on c2.id = c.city_id " +
//                    "join field f on f.id = r.field_id " +
//                    "join vacancies v on v.id = r.vacancies_id"+
//                    "where c.id = :idCompany",
//            countQuery = "select count(*) " +
//                    "from apply a " +
//                    "join recruitmentnew r on a.recruitmentnew_id = r.id " +
//                    "join company c on r.company_id = c.id " +
//                    "join city on c.id = c.city_id " +
//                    "join field f on f.id = r.field_id " +
//                    "join vacancies v on v.id = r.vacancies_id " +
//                    "where c.id = :idCompany",
//            nativeQuery = true
//    )
//    Page<ApplyShowAll> findAllByCompanyId(Pageable pageable, @Param("idCompany") Long idCompany);

    Page<Apply> findAllByUser_Id(Pageable pageable, Long id);
    boolean existsByUserIdAndRecruitmentNewId(Long userID, Long recruitmentID);
}
