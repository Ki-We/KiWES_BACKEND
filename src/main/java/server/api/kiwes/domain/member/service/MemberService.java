package server.api.kiwes.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import server.api.kiwes.domain.member.constant.MemberResponseType;
import server.api.kiwes.domain.member.entity.Member;
import server.api.kiwes.domain.member.repository.MemberRepository;
import server.api.kiwes.global.security.util.SecurityUtils;

import javax.transaction.Transactional;

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

    public String nicknameDuplicateCheck(String nickname) {
        if (memberRepository.findNotDeletedByNickname(nickname).isPresent()) {
            return MemberResponseType.EXISTED_NICKNAME.getMessage();
        } else {
            return MemberResponseType.VALID_NICKNAME.getMessage();
        }
    }




}
