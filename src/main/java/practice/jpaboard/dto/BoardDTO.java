package practice.jpaboard.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import practice.jpaboard.entity.Board;
import practice.jpaboard.entity.Member;

@Data
@NoArgsConstructor
@Builder
public class BoardDTO {

    private Long no;
    private String userId;
    private String title;
    private String content;
    private String regDt;
    private String modiDt;

    public Board toEntity(Member member) {
        return new Board(member, title, content);
    }

    @QueryProjection
    public BoardDTO(Long no, String userId, String title, String content, String regDt, String modiDt) {
        this.no = no;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.regDt = regDt;
        this.modiDt = modiDt;
    }
}
