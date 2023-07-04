package server.api.kiwes.domain.club_category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import server.api.kiwes.domain.category.entity.Category;
import server.api.kiwes.domain.club.entity.Club;
import server.api.kiwes.domain.club_category.entity.ClubCategory;

import java.util.List;

public interface ClubCategoryRepository extends JpaRepository<ClubCategory, Long> {


    @Query("select c.club from ClubCategory c where c.category.id = :categoryId")
    List<Club> findAllByCategoryId(@Param("categoryId") Long categoryId);
}
