package server.api.kiwes.domain.club_member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.api.kiwes.domain.club_member.entity.ClubMember;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
}
