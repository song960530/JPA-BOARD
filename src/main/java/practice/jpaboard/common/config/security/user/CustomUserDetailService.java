package practice.jpaboard.common.config.security.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import practice.jpaboard.entity.Member;
import practice.jpaboard.repository.MemberRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("ID를 찾을 수 없습니다."));

        return new User(member.getUserId(), member.getPassword(), member.getRoles().stream()
                .map(m -> new SimpleGrantedAuthority(m.getRoles()))
                .collect(Collectors.toList()));
    }
}
