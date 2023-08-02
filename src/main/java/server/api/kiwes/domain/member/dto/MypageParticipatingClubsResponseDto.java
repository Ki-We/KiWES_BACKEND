package server.api.kiwes.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.api.kiwes.domain.club.entity.Club;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MypageParticipatingClubsResponseDto {
    private Long clubId;
    private String thumbnailUrl;

    public static MypageParticipatingClubsResponseDto of(Club club){
        return MypageParticipatingClubsResponseDto.builder()
                .clubId(club.getId())
                .thumbnailUrl(club.getThumbnailUrl())
                .build();
    }
}
