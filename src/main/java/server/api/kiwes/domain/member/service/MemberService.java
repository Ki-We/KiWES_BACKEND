package server.api.kiwes.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import server.api.kiwes.domain.club_member.entity.ClubMember;
import server.api.kiwes.domain.club_member.repository.ClubMemberRepository;
import server.api.kiwes.domain.member.constant.MemberResponseType;
import server.api.kiwes.domain.member.dto.MyPageResponse;
import server.api.kiwes.domain.member.dto.MypageParticipatingClubsResponseDto;
import server.api.kiwes.domain.member.entity.Member;
import server.api.kiwes.domain.member.repository.MemberRepository;
import server.api.kiwes.global.security.util.SecurityUtils;
import server.api.kiwes.response.BizException;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final ClubMemberRepository clubMemberRepository;

    /**
     * 로그인된 Member 객체를 리턴하는 함수
     * @return Member
     */
    public Member getLoggedInMember(){
        return memberRepository.findById(SecurityUtils.getLoggedInUser().getId())
                .orElseThrow(()-> new BizException(MemberResponseType.NOT_LOGGED_IN_USER));
    }

    public Member findById(Long id){
        return memberRepository.findById(id)
                .orElseThrow(() -> new BizException(MemberResponseType.NOT_LOGGED_IN_USER));
    }

    /**
     * 마이페이지-프로필 이미지 변경 (presigned URL)
     */
    public String changeProfileImg() {

        Long memberId = SecurityUtils.getLoggedInUser().getId();
        Member member = memberRepository.findById(memberId).orElseThrow();
        member.setProfileImg("https://kiwes-bucket.s3.ap-northeast-2.amazonaws.com/profileimg/" + member.getNickname() + ".jpg");
        memberRepository.save(member);

        return member.getNickname();
    }
    /**
     * 닉네임 중복 체크
     */
    public String nicknameDuplicateCheck(String nickname) {
        if (memberRepository.findNotDeletedByNickname(nickname).isPresent()) {
            return MemberResponseType.EXISTED_NICKNAME.getMessage();
        } else {
            return MemberResponseType.VALID_NICKNAME.getMessage();
        }
    }
    /**
     * 자기소개 update
     */
    public String updateIntroduction(String introduction) {
        Long memberId = SecurityUtils.getLoggedInUser().getId();
        Member member = memberRepository.findById(memberId).orElseThrow();
        member.setIntroduction(introduction);
        memberRepository.save(member);

        return member.getNickname();
    }

    /**
     * 마이페이지 GET
     */
    public MyPageResponse myPage() throws ParseException {
        Long memberId = SecurityUtils.getLoggedInUser().getId();
        Member member = memberRepository.findById(memberId).orElseThrow();

        String birth = member.getBirth();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dueToDate = format.parse(birth);

        Date now = new Date();

        long diffInMillis = now.getTime() -  dueToDate.getTime();
        long diffInYears = diffInMillis / (1000L * 60L * 60L * 24L * 365L);

        //프로필 사진, 닉네임, 국적, 나이, 성별, 소개
        return new MyPageResponse(member.getProfileImg(), member.getNickname(), member.getNationality().getName(), String.valueOf(diffInYears), member.getGender().getName(), member.getIntroduction());

    }


    /**
     * 마이페이지 - 참여 모임
     */
    public List<MypageParticipatingClubsResponseDto> getMyParticipatingClubs(Member member) {

        return clubMemberRepository.findByMember(member).stream()
                .filter(ClubMember::getIsApproved)
                .map(clubMember -> MypageParticipatingClubsResponseDto.of(clubMember.getClub()))
                .collect(Collectors.toList());
    }
}
