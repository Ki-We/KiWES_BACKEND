package server.api.kiwes.domain.member.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import server.api.kiwes.response.BaseResponseType;

@Getter
@AllArgsConstructor
public enum MemberResponseType implements BaseResponseType {

    LOGIN_SUCCESS(20001,"로그인 성공",HttpStatus.OK),
    SIGN_UP_SUCCESS(20002, "회원가입 성공", HttpStatus.OK),
    PROFILE_IMG_SUCCESS(20003, "프로필 이미지 업데이트 성공", HttpStatus.OK),

    SIGN_UP_ING(40003,"회원가입 진행중", HttpStatus.BAD_REQUEST),

    KAKAO_CONNECT_ERROR(40001, "카카오 연결 에러", HttpStatus.BAD_REQUEST),
    NOT_FOUND_EMAIL(40002,"이메일을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),


    ;

    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;


    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
