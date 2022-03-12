package practice.jpaboard.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import practice.jpaboard.common.config.ResultMessage;
import practice.jpaboard.common.config.security.JwtTokenProvider;
import practice.jpaboard.dto.JoinDTO;
import practice.jpaboard.entity.Member;
import practice.jpaboard.entity.Role;
import practice.jpaboard.repository.MemberRepository;
import practice.jpaboard.repository.RoleRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;
    @Spy
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private RoleRepository roleRepository;

    private JoinDTO joinDto() {
        return JoinDTO.builder()
                .userId("test")
                .password("패스워드")
                .name("이름")
                .age(20)
                .build();
    }

    @Test
    @DisplayName("회원가입 성공")
    public void joinSuccess() throws Exception {
        // given
        JoinDTO joinDto = joinDto();
        passwordEncoder = new BCryptPasswordEncoder();
        String encrytPassword = passwordEncoder.encode(joinDto.getPassword());


        // when
        doReturn(new Role(1L, "ROLE_USER")).when(roleRepository).findByRoles("ROLE_USER");
        doReturn(new Member(joinDto().getUserId(), joinDto().getPassword(), joinDto().getName(), joinDto().getAge()))
                .when(memberRepository).save(any(Member.class));
        ResultMessage result = memberService.join(joinDto);

        // then
        assertEquals(true, result.isSuccess());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}