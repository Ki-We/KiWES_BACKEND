package server.api.kiwes.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.api.kiwes.domain.club.entity.Club;
import server.api.kiwes.domain.member.entity.Member;
import server.api.kiwes.domain.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Boolean existsByClubAndMember(Club club, Member member);
}
