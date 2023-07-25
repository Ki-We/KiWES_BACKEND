package server.api.kiwes.domain.heart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.api.kiwes.domain.club.entity.Club;
import server.api.kiwes.domain.heart.constant.HeartStatus;
import server.api.kiwes.domain.heart.entity.Heart;
import server.api.kiwes.domain.heart.repository.HeartRepository;
import server.api.kiwes.domain.member.entity.Member;

@Service
@Transactional
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepository heartRepository;

    /**
     * 클럽, 멤버를 가지고 heart 여부 리턴
     */
    public Boolean getHearted(Club club, Member member){
        Heart heart = heartRepository.findByClubAndMember(club, member)
                .orElse(null);

        if (heart == null) return false;
        return !heart.getStatus().equals(HeartStatus.NO);
    }

    public void heart(Member member, Club club) {
        Heart heart = heartRepository.findByClubAndMember(club, member)
                .orElse(null);

        if (heart == null){
            Heart newHeart = Heart.builder()
                    .club(club)
                    .member(member)
                    .build();

            heartRepository.save(newHeart);
            return;
        }

        heart.setStatus(HeartStatus.YES);
    }

    public void unheart(Member member, Club club) {
        Heart heart = heartRepository.findByClubAndMember(club, member)
                .orElse(null);

        if(heart == null) return;

        heart.setStatus(HeartStatus.NO);
    }
}
