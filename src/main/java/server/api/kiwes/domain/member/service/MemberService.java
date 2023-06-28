package server.api.kiwes.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
<<<<<<< Updated upstream
=======
import server.api.kiwes.domain.member.constant.MemberResponseType;
import server.api.kiwes.domain.member.dto.MyPageResponse;
>>>>>>> Stashed changes
import server.api.kiwes.domain.member.entity.Member;
import server.api.kiwes.domain.member.repository.MemberRepository;
import server.api.kiwes.global.security.util.SecurityUtils;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public String changeProfileImg() {

        Long memberId = SecurityUtils.getLoggedInUser().getId();
        Member member = memberRepository.findById(memberId).orElseThrow();
        member.setProfileImg("https://kiwes-bucket.s3.ap-northeast-2.amazonaws.com/profileimg/" + member.getNickname() + ".jpg");
        memberRepository.save(member);

        return member.getNickname();
    }

<<<<<<< Updated upstream
=======
    public String nicknameDuplicateCheck(String nickname) {
        if (memberRepository.findNotDeletedByNickname(nickname).isPresent()) {
            return MemberResponseType.EXISTED_NICKNAME.getMessage();
        } else {
            return MemberResponseType.VALID_NICKNAME.getMessage();
        }
    }

    public String updateIntroduction(String introduction) {
        Long memberId = SecurityUtils.getLoggedInUser().getId();
        Member member = memberRepository.findById(memberId).orElseThrow();
        member.setIntroduction(introduction);
        memberRepository.save(member);

        return member.getNickname();
    }

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






>>>>>>> Stashed changes

}
