package practice.jpaboard.domain.board.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import practice.jpaboard.domain.board.entity.Board;
import practice.jpaboard.domain.member.entity.Member;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDto {

    private Long no;
    private String userId;
    private String title;
    private String content;
    private String regDt;
    private String modiDt;
    private Boolean like;
    private List<UploadDto> uploadList = new ArrayList<>();

    public Board toEntity(Member member) {
        return new Board(member, title, content);
    }

    @QueryProjection
    public BoardDto(Long no, String userId, String title, String content, String regDt, String modiDt) {
        this.no = no;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.regDt = regDt;
        this.modiDt = modiDt;
    }
}
