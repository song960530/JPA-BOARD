package practice.jpaboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.jpaboard.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class memberService {
    private final MemberRepository memberRepository;
}
