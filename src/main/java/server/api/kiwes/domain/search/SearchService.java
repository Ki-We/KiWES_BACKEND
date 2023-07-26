package server.api.kiwes.domain.search;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.api.kiwes.domain.club.entity.Club;
import server.api.kiwes.domain.club.repository.ClubRepository;
import server.api.kiwes.domain.member.entity.Member;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SearchService {
    private final ClubRepository clubRepository;
    public List<SearchResponseDto> search(String keyword, Member member) {
        return clubRepository.findByTitleContaining(keyword).stream().map(club -> SearchResponseDto.of(club, member)).collect(Collectors.toList());
    }
}
