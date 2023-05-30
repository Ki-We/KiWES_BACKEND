package server.api.kiwes.domain.member.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.api.kiwes.base.dto.FooDto;
import server.api.kiwes.domain.member.constant.MemberResponseType;
import server.api.kiwes.domain.member.dto.AdditionInfoRequest;
import server.api.kiwes.domain.member.dto.LoginRequest;
import server.api.kiwes.domain.member.service.auth.MemberAuthenticationService;
import server.api.kiwes.response.BizException;
import server.api.kiwes.response.ResponseMessage;
import server.api.kiwes.response.foo.FooResponseType;

import javax.validation.Valid;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/members")
@Api(tags = "Member API")
@Slf4j
public class MemberController {

    private final MemberAuthenticationService authenticationService;

    /**
     * API
     * @param loginRequest
     * @return ResponseMessage
     * @Author Seungyeon, Jeong
     */
    @ApiOperation(value = "요청 예시", notes = "요청 예시입니다!\nname에 \"error\" : controller에서 error 처리하는 경우\ntitle에 \"error\" : service에서 error 처리하는 경우")
    @ApiResponses({
            @ApiResponse(code = 20001, message = "로그인 객체 정상 리턴 (200 OK)"),
            @ApiResponse(code = 40001, message = "parameter 누락 (400 BAD_REQUEST)")
    })
    @PostMapping("/auth/kakao")
    public ResponseEntity<ResponseMessage> login(
            @Parameter(name = "카카오 로그인 객체", description = "카카오 로그인을 위한 객체", in = QUERY, required = false) @RequestBody(required = false) LoginRequest loginRequest
    ){

//        if (loginRequest == null || loginRequest.equals("error")){
//            log.error(FooResponseType.INVALID_PARAMETER.getMessage());
//            throw new BizException(FooResponseType.INVALID_PARAMETER);
//        }
        return new ResponseEntity<>(ResponseMessage.create(MemberResponseType.LOGIN_SUCCESS, authenticationService.login(loginRequest)), MemberResponseType.LOGIN_SUCCESS.getHttpStatus());
    }

    /**
     * API
     * @param additionInfoRequest
     * @return ResponseMessage
     * @Author Seungyeon, Jeong
     */

    @ApiOperation(value = "추가 정보 입력", notes = "추가 정보를 입력합니다.")
    @ApiResponses({
            @ApiResponse(code = 20001, message = "Foo 객체 정상 리턴 (200 OK)"),
            @ApiResponse(code = 40001, message = "parameter 누락 (400 BAD_REQUEST)")
    })
    @GetMapping("/additional-info")
    public ResponseEntity<ResponseMessage> login(
            @Parameter(name = "추가 정보 입력 객체", description = "회원가입시 추가정보 입력 위한 객체", in = QUERY, required = false) @RequestParam(required = false) AdditionInfoRequest additionInfoRequest
    ){

        if (additionInfoRequest == null || additionInfoRequest.equals("error")){
            log.error(FooResponseType.INVALID_PARAMETER.getMessage());
            throw new BizException(FooResponseType.INVALID_PARAMETER);
        }
        return new ResponseEntity<>(ResponseMessage.create(MemberResponseType.LOGIN_SUCCESS, authenticationService.signUp(additionInfoRequest)), MemberResponseType.SIGN_UP_SUCCESS.getHttpStatus());
    }

}
