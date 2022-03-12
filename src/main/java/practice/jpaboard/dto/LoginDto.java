package practice.jpaboard.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import practice.jpaboard.entity.Member;

@Data
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
