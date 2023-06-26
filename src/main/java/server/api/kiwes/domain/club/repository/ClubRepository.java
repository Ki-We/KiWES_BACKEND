package server.api.kiwes.domain.club.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.api.kiwes.domain.club.entity.Club;

public interface ClubRepository extends JpaRepository<Club, Long> {
}
