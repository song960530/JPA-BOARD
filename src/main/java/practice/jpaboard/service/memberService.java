package practice.jpaboard.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.jpaboard.common.config.ResultMessage;
import practice.jpaboard.common.config.security.JwtTokenProvider;
import practice.jpaboard.dto.JoinDto;
import practice.jpaboard.dto.LoginDto;
import practice.jpaboard.entity.Member;
import practice.jpaboard.repository.MemberRepository;
import practice.jpaboard.repository.RoleRepository;

@Service
@Transactional(readOnly = true)
public class MemberService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final CommonService commonService;

    public MemberService(BCryptPasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, MemberRepository memberRepository, RoleRepository roleRepository, CommonService commonService) {
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
        this.commonService = commonService;
    }

    @Transactional
    public ResultMessage join(JoinDto joinDto) {

        try {
            joinDto.setRole(roleRepository.findByRoles("ROLE_USER")); // 앞에 ROLE_은 시큐리티에서 default prefix로 정의해둔거라 꼭 붙여야함
            joinDto.setEncrytPassword(passwordEncoder.encode(joinDto.getPassword()));

            memberRepository.save(joinDto.toEntity());
        } catch (Exception e) {
            return ResultMessage.of(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResultMessage.of(true, HttpStatus.OK);
    }


    public ResultMessage login(LoginDto loginDto) {
        Member member = memberRepository.findByUserId(loginDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("가입되지 않은 ID입니다"));

        if (!passwordEncoder.matches(loginDto.getPassword(), member.getPassword()))
            throw new IllegalArgumentException("패스워드가 맞지 않습니다");

        String token = jwtTokenProvider.createToken(member.getUserId(), member.getRoles());


        return ResultMessage.of(true, new LoginDto(member, token), HttpStatus.OK);
    }
}
