package server.api.kiwes.domain.search_count.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SearchCount {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEARCH_COUNT_ID")
    private Long id;

    private String date;
    private String searchWord;
    private Integer count;

    // Scheduler를 통해 일정 기간이 지난 이후에는 데이터를 삭제하도록 하기
}
