package practice.jpaboard.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "SEQ_LIKE_GENERATOR"
        , sequenceName = "SEQ_LIKE"
        , initialValue = 1
        , allocationSize = 1
)
@Table(name = "LIKES")
public class Like {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
            , generator = "SEQ_LIKE_GENERATOR"
    )
    @Column(name = "LIKE_NO")
    private Long no;

    @JoinColumn(name = "MEMBER_NO")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "BOARD_NO")
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    public Like(Member member, Board board) {
        this.member = member;
        this.board = board;
    }
}
