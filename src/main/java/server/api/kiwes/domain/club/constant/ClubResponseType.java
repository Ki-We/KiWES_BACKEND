package server.api.kiwes.domain.club.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import server.api.kiwes.response.BaseResponseType;

@Getter
@AllArgsConstructor
public enum ClubResponseType implements BaseResponseType {
    POST_SUCCESS(20101, "모임 모집 글 업로드 성공", HttpStatus.OK),
    ;

    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;
}
