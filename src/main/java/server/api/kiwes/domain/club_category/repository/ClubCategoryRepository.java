package server.api.kiwes.domain.club_category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.api.kiwes.domain.club_category.entity.ClubCategory;

public interface ClubCategoryRepository extends JpaRepository<ClubCategory, Long> {
}
