package practice.jpaboard.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import practice.jpaboard.entity.Member;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class MemberRepositoryTest {
//    @Autowired
//    MemberRepository memberRepository;
//
//    @Test
//    @DisplayName("스프링데이터JPA 기본동작을 확인한다")
//    public void testSpringDataJpa() throws Exception {
//        // given
//        Member member = new Member("test", "test", "test", 11);
//
//        // when
//        memberRepository.save(member);
//        Member findMember = memberRepository.findByUserId("test").orElse(null);
//
//        // then
//        assertEquals(member.getUserId(), findMember.getUserId());
//    }

}