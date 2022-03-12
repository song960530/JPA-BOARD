package practice.jpaboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import practice.jpaboard.common.config.security.JwtTokenProvider;
import practice.jpaboard.dto.JoinDto;
import practice.jpaboard.entity.Member;
import practice.jpaboard.entity.Role;
import practice.jpaboard.repository.MemberRepository;
import practice.jpaboard.repository.RoleRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;

    @PostMapping("/join")
    public void join(@RequestBody JoinDto joinDto) {
        joinDto.setRole(roleRepository.findByRoles("ROLE_USER")); // 앞에 ROLE_은 시큐리티에서 default prefix로 정의해둔거라 꼭 붙여야함
        joinDto.setEncrytPassword(passwordEncoder.encode(joinDto.getPassword()));

        memberRepository.save(joinDto.toEntity());
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        Member member = memberRepository.findByUserId(user.get("userId")).orElseThrow(() -> new IllegalArgumentException("가입되지 않은 ID입니다"));

        if (!passwordEncoder.matches(user.get("password"), member.getPassword()))
            throw new IllegalArgumentException("패스워드가 맞지 않습니다");

        return jwtTokenProvider.createToken(member.getUserId(), member.getRoles());

    }

}
