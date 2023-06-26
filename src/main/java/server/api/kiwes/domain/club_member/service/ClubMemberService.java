package server.api.kiwes.domain.club_member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.api.kiwes.domain.club.entity.Club;
import server.api.kiwes.domain.club_member.entity.ClubMember;
import server.api.kiwes.domain.club_member.repository.ClubMemberRepository;
import server.api.kiwes.domain.member.entity.Member;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubMemberService {
    private final ClubMemberRepository clubMemberRepository;

    /**
     * 모임과 멤버를 통해 ClubMember 객체 반환. 없으면 null 반환
     */
    public ClubMember findByClubAndMember(Club club, Member member){
        return clubMemberRepository.findByClubAndMember(club, member)
                .orElse(null);
    }

    /**
     * 모임과 멤버 정보로, 해당 사용자가 호스트인지 여부를 반환
     */
    public Boolean getIsHost(Club club, Member member){
        ClubMember clubMember = findByClubAndMember(club, member);
        if (clubMember == null) return false;

        return clubMember.getIsHost();
    }

}
