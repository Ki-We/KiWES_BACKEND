package server.api.kiwes.domain.member.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import server.api.kiwes.domain.member.constant.MemberResponseType;
import server.api.kiwes.domain.member.dto.AdditionInfoRequest;
import server.api.kiwes.domain.member.service.auth.MemberAuthenticationService;
import server.api.kiwes.response.BizException;
import server.api.kiwes.response.ApiResponse;
import server.api.kiwes.response.foo.FooResponseType;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/members")
@Api(tags = "Member")
@Slf4j
public class MemberController {

    private final MemberAuthenticationService authenticationService;

    /**
     * API
     * @return ApiResponse
     * @Author Seungyeon, Jeong
     */
    @ApiOperation(value = "카카오 로그인", notes = "카카오 로그인 API")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 20001, message = "로그인 객체 정상 리턴 (200 OK)"),
            @io.swagger.annotations.ApiResponse(code = 40001, message = "parameter 누락 (400 BAD_REQUEST)")
    })
    @PostMapping("/auth/kakao")
    public ApiResponse<Object> login(
            @RequestHeader(name = "Authorization") String token
    ){

        return ApiResponse.of(MemberResponseType.LOGIN_SUCCESS,authenticationService.login(token));
    }

    /**
     * API
     * @param additionInfoRequest
     * @return ResponseMessage
     * @Author Seungyeon, Jeong
     */

    @ApiOperation(value = "추가 정보 입력", notes = "추가 정보를 입력합니다.")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 20001, message = "Foo 객체 정상 리턴 (200 OK)"),
            @io.swagger.annotations.ApiResponse(code = 40001, message = "parameter 누락 (400 BAD_REQUEST)")
    })
    @PostMapping("/additional-info")
    public ApiResponse<Object> signUp(
            @Parameter(name = "추가 정보 입력 객체", description = "회원가입시 추가정보 입력 위한 객체", in = QUERY, required = false) @RequestBody(required = false) AdditionInfoRequest additionInfoRequest
    ){


        if (additionInfoRequest == null || additionInfoRequest.equals("error")){
            log.error(FooResponseType.INVALID_PARAMETER.getMessage());
            throw new BizException(FooResponseType.INVALID_PARAMETER);
        }

        authenticationService.signUp(additionInfoRequest);

        return ApiResponse.of(MemberResponseType.SIGN_UP_SUCCESS);
    }

}
