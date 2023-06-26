package server.api.kiwes.domain.club.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import server.api.kiwes.response.BaseResponseType;

@Getter
@AllArgsConstructor
public enum ClubResponseType implements BaseResponseType {
    POST_SUCCESS(20101, "모임 모집 글 업로드 성공", HttpStatus.OK),
    DELETE_SUCCESS(20102, "모임 모집 글 삭제 성공", HttpStatus.OK),

    CLUB_NOT_EXIST(40101, "clubId와 일치하는 모임이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    ;

    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;
}
