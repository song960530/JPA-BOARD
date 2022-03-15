package practice.jpaboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import practice.jpaboard.entity.Member;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    private String userId;
    private String password;
    private int age;
    private String name;
    private String token;

    public LoginDto(Member member, String token) {
        this.userId = member.getUserId();
        this.age = member.getAge();
        this.name = member.getName();
        this.token = token;
    }
}
