package practice.jpaboard.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@SequenceGenerator(
        name = "SEQ_ROLE_GENERATOR"
        , sequenceName = "SEQ_ROLE"
        , initialValue = 1
        , allocationSize = 1
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_ROLE_GENERATOR")
    @Column(name = "ROLE_NO")
    private Long no;

    @Column(name = "ROLES")
    private String roles;

    @ManyToMany
    @JoinTable(
            name = "MEMBER_ROLES"
            , joinColumns = @JoinColumn(name = "ROLE_NO")
            , inverseJoinColumns = @JoinColumn(name = "MEMBER_NO")
    )
    private List<Member> members;
}
