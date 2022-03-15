package practice.jpaboard.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import practice.jpaboard.entity.Member;
import practice.jpaboard.repository.MemberRepository;

@Service
@Slf4j
public class CommonService {
    private final MemberRepository memberRepository;

    public CommonService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member findUserIdFromAuth() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return memberRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("가입되지 않은 ID입니다"));
    }
}
