package practice.jpaboard.domain.board.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import practice.jpaboard.domain.model.BaseTimeEntity;
import practice.jpaboard.domain.member.entity.Member;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "SEQ_UPLOAD_GENERATOR"
        , sequenceName = "SEQ_UPLOAD"
        , initialValue = 1
        , allocationSize = 1
)
public class Upload extends BaseTimeEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
            , generator = "SEQ_UPLOAD_GENERATOR"
    )
    @Column(name = "UPLOAD_NO")
    private Long no;

    @JoinColumn(name = "MEMBER_NO")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "BOARD_NO")
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @Column(name = "ORIGIN_NAME")
    private String originName;

    @Column(name = "ENCRYPT_NAME")
    private String encryptName;

    public Upload(Member member, Board board, String originName, String encryptName) {
        this.member = member;
        this.board = board;
        this.originName = originName;
        this.encryptName = encryptName;
    }
}
