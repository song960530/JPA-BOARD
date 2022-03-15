package practice.jpaboard.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import practice.jpaboard.domain.member.service.MemberService;
import practice.jpaboard.global.common.response.ResultMessage;
import practice.jpaboard.global.config.jwt.JwtTokenProvider;
import practice.jpaboard.domain.member.dto.JoinDto;
import practice.jpaboard.domain.member.dto.LoginDto;
import practice.jpaboard.domain.member.entity.Member;
import practice.jpaboard.domain.member.entity.Role;
import practice.jpaboard.domain.member.repository.MemberRepository;
import practice.jpaboard.domain.member.repository.RoleRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    private MemberService memberService;
    private BCryptPasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserDetailsService userDetailsService;

    @BeforeEach
    void setup() {
        passwordEncoder = new BCryptPasswordEncoder();
        jwtTokenProvider = new JwtTokenProvider(userDetailsService);
        memberService = new MemberService(passwordEncoder, jwtTokenProvider, memberRepository, roleRepository);
    }

    private JoinDto joinDto() {
        return JoinDto.builder()
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
        JoinDto joinDto = joinDto();

        // when
        doReturn(new Role(1L, "ROLE_USER")).when(roleRepository).findByRoles(anyString());
        doReturn(new Member(joinDto().getUserId(), joinDto().getPassword(), joinDto().getName(), joinDto().getAge()))
                .when(memberRepository).save(any(Member.class));
        ResultMessage result = memberService.join(joinDto);

        // then
        assertEquals(true, result.isSuccess());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @DisplayName("회원가입 실패")
    public void joinFail() throws Exception {
        // given
        JoinDto joinDto = joinDto();

        // when
        doReturn(new Role(1L, "ROLE_USER")).when(roleRepository).findByRoles(anyString());
        doReturn(Exception.class).when(memberRepository).save(any(Member.class));
        ResultMessage result = memberService.join(joinDto);

        // then
        assertEquals(false, result.isSuccess());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    @DisplayName("ID 조회되지 않음")
    public void loginNotFoundId() throws Exception {
        // given
        LoginDto loginDTO = LoginDto.builder()
                .userId("test")
                .password("password")
                .build();

        // when
        doThrow(new IllegalArgumentException()).when(memberRepository).findByUserId(anyString());

        // then
        assertThrows(IllegalArgumentException.class, () -> {
            memberService.login(loginDTO);
        });
    }

    @Test
    @DisplayName("패스워드 맞지 않음")
    public void loginNotMatchedPassword() throws Exception {
        // given
        LoginDto loginDTO = LoginDto.builder()
                .userId("test")
                .password("password")
                .build();
        String encryptPassword = new BCryptPasswordEncoder().encode(loginDTO.getPassword());

        // when
        doReturn(Optional.of(new Member("test", encryptPassword + "111", "test", 20))).when(memberRepository)
                .findByUserId(anyString());

        // then
        assertThrows(IllegalArgumentException.class, () -> {
            memberService.login(loginDTO);
        });
    }

    @Test
    @DisplayName("로그인 성공")
    public void loginSuccess() throws Exception {
        // given
        LoginDto loginDTO = LoginDto.builder()
                .userId("test")
                .password("password")
                .build();
        String encryptPassword = new BCryptPasswordEncoder().encode(loginDTO.getPassword());

        // when
        doReturn(Optional.of(new Member(loginDTO.getUserId(), encryptPassword, "test", 20, Arrays.asList(new Role(1L, "ROLE_USER")))))
                .when(memberRepository).findByUserId(loginDTO.getUserId());
        LoginDto result = (LoginDto) memberService.login(loginDTO).getData();

        // then
        assertThat(result.getToken()).isNotNull();
    }
}