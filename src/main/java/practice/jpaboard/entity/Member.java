package practice.jpaboard.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@SequenceGenerator(
        name = "SEQ_MEMBER_GENERATOR"
        , sequenceName = "SEQ_MEMBER"
        , initialValue = 1
        , allocationSize = 1
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseTimeEntity implements UserDetails {
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

    @ManyToMany
    @JoinTable(
            name = "MEMBER_ROLES"
            , joinColumns = @JoinColumn(name = "MEMBER_NO")
            , inverseJoinColumns = @JoinColumn(name = "ROLE_NO")
    )
    private List<Role> roles = new ArrayList<>();

    public Member(String userId, String password, String name, int age) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.age = age;
    }

    public Member(String userId, String password, String name, int age, List<Role> roles) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.age = age;
        this.roles = roles;
    }

    /**
     * ***********************************
     * SpringSecurity UserDetails 구현 메서드
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(m -> new SimpleGrantedAuthority(m.getRoles()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    /**
     * ***********************************
     */
}
