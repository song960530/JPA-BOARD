package practice.jpaboard.domain.board.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class commentDto {
    private Long commentNo;
    private Long boardNo;
    private String userId;
    private Long parent;
    private String content;

    @QueryProjection
    public commentDto(Long commentNo, Long boardNo, String userId, Long parent, String content) {
        this.commentNo = commentNo;
        this.boardNo = boardNo;
        this.userId = userId;
        this.parent = parent;
        this.content = content;
    }
}
