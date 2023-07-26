package server.api.kiwes.domain.search;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.api.kiwes.domain.member.service.MemberService;
import server.api.kiwes.response.ApiResponse;

import java.util.List;

@Api(tags = "Search - 검색")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SearchController {
    private final SearchService searchService;
    private final MemberService memberService;

    @ApiOperation(value = "검색")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 21301, message = "검색 결과 응답 성공"),
    })
    @GetMapping("/search")
    public ApiResponse<List<SearchResponseDto>> search(@RequestParam String keyword){

        return ApiResponse.of(SearchResponseType.SEARCH_SUCCESS, searchService.search(keyword, memberService.getLoggedInMember()));
    }
}
