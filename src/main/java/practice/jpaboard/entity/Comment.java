package practice.jpaboard.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "SEQ_COMMENT_GENERATOR"
        , sequenceName = "SEQ_COMMENT"
        , initialValue = 1
        , allocationSize = 1
)
@Table(name = "COMMENTS")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
            , generator = "SEQ_COMMENT_GENERATOR"
    )
    @Column(name = "COMMENT_NO")
    private Long no;

    @JoinColumn(name = "MEMBER_NO")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "BOARD_NO")
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @Column(name = "PARENT")
    private Long parent;

    @Column(name = "CONTENT")
    private String content;

    @PrePersist
    private void prePersist() {
        this.parent = (this.parent == null ? 0 : this.parent);
    }

    public Comment(Member member, Board board, String content) {
        this.member = member;
        this.board = board;
        this.content = content;
    }
}
