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
    APPLICATION_SUCCESS(20103, "참여 신청 성공", HttpStatus.OK),
    WITHDRAWAL_SUCCESS(20104, "참여 취소 성공", HttpStatus.OK),
    APPROVE_SUCCESS(20105, "멤버 승인 성공", HttpStatus.OK),

    CLUB_NOT_EXIST(40101, "clubId와 일치하는 모임이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    ALREADY_APPLIED(40102, "호스트이거나 이미 참여신청함.", HttpStatus.BAD_REQUEST),
    NOT_HOST(40103, "호스트가 아님", HttpStatus.UNAUTHORIZED),
    NOT_APPLIED(40104, "모임의 지원한 사용자가 아님", HttpStatus.BAD_REQUEST),
    ;

    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;
}
