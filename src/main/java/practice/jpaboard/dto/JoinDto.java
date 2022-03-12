package practice.jpaboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import practice.jpaboard.entity.Member;
import practice.jpaboard.entity.Role;

import java.util.Arrays;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinDto {
    private String userId;
    private String password;
    private String encrytPassword;
    private String name;
    private int age;
    private Role role;

    public Member toEntity() {
        return new Member(userId, encrytPassword, name, age, Arrays.asList(role));
    }
}
