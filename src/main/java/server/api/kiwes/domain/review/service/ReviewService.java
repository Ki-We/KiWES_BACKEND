package server.api.kiwes.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.api.kiwes.domain.club.entity.Club;
import server.api.kiwes.domain.member.entity.Member;
import server.api.kiwes.domain.review.constant.ReviewResponseType;
import server.api.kiwes.domain.review.dto.ReviewRegisterDto;
import server.api.kiwes.domain.review.entity.Review;
import server.api.kiwes.domain.review.repository.ReviewRepository;
import server.api.kiwes.response.BizException;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    /**
     * 후기 등록
     */
    public void postReview(Club club, Member member, ReviewRegisterDto registerDto) {
        if(reviewRepository.existsByClubAndMember(club, member)){
            throw new BizException(ReviewResponseType.ALREADY_POSTED);
        }

        reviewRepository.save(Review.builder()
                .club(club)
                .member(member)
                .content(registerDto.getContent())
                .build());
    }
}
