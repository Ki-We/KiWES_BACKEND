package server.api.kiwes.domain.club_member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.api.kiwes.domain.club.entity.Club;
import server.api.kiwes.domain.club_member.entity.ClubMember;
import server.api.kiwes.domain.member.entity.Member;

import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
    Optional<ClubMember> findByClubAndMember(Club club, Member member);

}
