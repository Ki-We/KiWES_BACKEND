package server.api.kiwes.domain.club.dto;

import lombok.*;
import server.api.kiwes.domain.heart.constant.HeartStatus;
import server.api.kiwes.domain.language.type.LanguageType;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClubSortResponseDto {

    Long clubId;
    String title;
    String thumbnailImage;
    String date;
    String location;
    List<LanguageType> languages;
    HeartStatus isHeart;

    public ClubSortResponseDto(Long clubId, String title, String thumbnailImage, String date, String location) {
        this.clubId = clubId;
        this.title = title;
        this.thumbnailImage = thumbnailImage;
        this.date = date;
        this.location = location;

//        List<String> typeToString = new ArrayList<>();
//
//        for (Object language : languages) {
//            typeToString.add(language.getClass().getName().toString());
//        }
//
//        this.languages = typeToString;
    }
}



