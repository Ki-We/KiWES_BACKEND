package server.api.kiwes.domain.member.service.auth;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.api.kiwes.domain.member.dto.AdditionInfoRequest;
import server.api.kiwes.domain.member.dto.LoginResponse;
import server.api.kiwes.domain.member.entity.Member;
import server.api.kiwes.domain.member.repository.MemberRepository;
import server.api.kiwes.domain.member.service.kakao.MemberKakaoService;
import server.api.kiwes.domain.member.service.validate.MemberValidationService;
import server.api.kiwes.global.dto.TokenInfoResponse;
import server.api.kiwes.global.entity.Gender;
import server.api.kiwes.global.jwt.TokenProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static server.api.kiwes.domain.member.constant.MemberResponseType.LOGIN_SUCCESS;
import static server.api.kiwes.domain.member.constant.MemberResponseType.SIGN_UP_ING;
import static server.api.kiwes.domain.member.constant.MemberServiceMessage.LOGIN_URL;
import static server.api.kiwes.domain.member.constant.Role.ROLE_USER;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberAuthenticationService {

    private final MemberRepository memberRepository;

    private final MemberKakaoService kakaoService;
    private final MemberValidationService validateService;
    private final TokenProvider tokenProvider;


    public LoginResponse login(String token) {

        // access token 으로 사용자 정보 가져오기
        JsonObject memberInfo = kakaoService.connectKakao(LOGIN_URL.getValue(), token);
        Member member = saveMember(kakaoService.getEmail(memberInfo), kakaoService.getProfileUrl(memberInfo),kakaoService.getGender(memberInfo));
        boolean isSignedUp = member.getEmail() != null;

        //2. 스프링 시큐리티 처리
        List<GrantedAuthority> authorities = initAuthorities();
        OAuth2User userDetails = createOAuth2UserByJson(authorities, memberInfo, kakaoService.getEmail(memberInfo));
        OAuth2AuthenticationToken auth = configureAuthentication(userDetails, authorities);

        //3. JWT 토큰 생성
        TokenInfoResponse tokenInfoResponse = tokenProvider.createToken(auth, isSignedUp, member.getId());
        return LoginResponse.from(tokenInfoResponse, isSignedUp ? LOGIN_SUCCESS.getMessage() : SIGN_UP_ING.getMessage(), member.getId());

    }

    public LoginResponse signUp(AdditionInfoRequest additionInfoRequest) {

        //추가 정보 입력 시
        //1. 프론트엔드에게 받은 (자체) 액세스 토큰 이용해서 사용자 이메일 가져오기
        Authentication authentication = tokenProvider.getAuthentication(additionInfoRequest.getAccessToken());
        Member member = validateService.validateEmail(authentication.getName());

        //2. 추가 정보 저장
        member.setMember(additionInfoRequest.getNickName(), additionInfoRequest.getBirth(), additionInfoRequest.getIntroduction(),additionInfoRequest.getNationality());
        memberRepository.save(member);

        //3. 스프링 시큐리티 처리
        List<GrantedAuthority> authorities = initAuthorities();
        OAuth2User userDetails = createOAuth2UserByMember(authorities, member);
        OAuth2AuthenticationToken auth = configureAuthentication(userDetails, authorities);

        //4. JWT 토큰 생성
        TokenInfoResponse tokenInfoResponse = tokenProvider.createToken(auth, true, member.getId());
        return LoginResponse.from(tokenInfoResponse, LOGIN_SUCCESS.getMessage(), member.getId());

    }

    public Member saveMember(String email,String profileImg, String gender) {

        Member member = new Member(email,profileImg, Gender.valueOf(gender.toUpperCase()));
        // 가입 여부 확인
        if (!memberRepository.existsByEmail(member.getEmail())) {
            memberRepository.save(member);
        }

        return memberRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다."));

    }

    public List<GrantedAuthority> initAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(String.valueOf(ROLE_USER)));
        return authorities;
    }

    public OAuth2User createOAuth2UserByMember(List<GrantedAuthority> authorities, Member member) {
        Map<String, Object> memberMap = new HashMap<>();
        memberMap.put("email", member.getEmail());
        memberMap.put("profileImg", member.getProfileImg());
        memberMap.put("nickname", member.getNickname());
        memberMap.put("birth", member.getBirth());
        memberMap.put("introduction", member.getIntroduction());
        memberMap.put("nationality", member.getNationality());
        OAuth2User userDetails = new DefaultOAuth2User(authorities, memberMap, "email");
        return userDetails;
    }

    /**
     * userInfo, email -> OAuth2User
     *
     * @param authorities
     * @param userInfo
     * @param email
     * @return OAuth2User
     */

    private OAuth2User createOAuth2UserByJson(List<GrantedAuthority> authorities, JsonObject userInfo, String email) {
        Map<String, Object> memberMap = new HashMap<>();
        memberMap.put("email", email);
        memberMap.put("profileUrl", kakaoService.getProfileUrl(userInfo));
        memberMap.put("gender", kakaoService.getGender(userInfo));
        authorities.add(new SimpleGrantedAuthority(String.valueOf(ROLE_USER)));
        return new DefaultOAuth2User(authorities, memberMap, "email");
    }



    public OAuth2AuthenticationToken configureAuthentication(OAuth2User userDetails, List<GrantedAuthority> authorities) {
        OAuth2AuthenticationToken auth = new OAuth2AuthenticationToken(userDetails, authorities, "email");
        auth.setDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(auth);
        return auth;
    }

}




