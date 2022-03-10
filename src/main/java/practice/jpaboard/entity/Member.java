package practice.jpaboard.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@SequenceGenerator(
        name = "SEQ_MEMBER_GENERATOR"
        , sequenceName = "SEQ_MEMBER"
        , initialValue = 1
        , allocationSize = 1
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseTimeEntity {
    @Id
    @Column(name = "MEMBER_NO")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
            , generator = "SEQ_MEMBER_GENERATOR"
    )
    private Long no;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NAME")
    private String name;

    @Column(name = "AGE")
    private int age;

    public Member(String userId, String password, String name, int age) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.age = age;
    }
}
