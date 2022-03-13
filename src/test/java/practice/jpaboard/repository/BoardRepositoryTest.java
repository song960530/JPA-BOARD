package practice.jpaboard.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import practice.jpaboard.entity.Board;
import practice.jpaboard.entity.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardRepositoryTest {
//
//    @Autowired
//    MemberRepository memberRepository;
//
//    @Autowired
//    BoardRepository boardRepository;
//
//    @Test
//    @DisplayName("스프링데이터JPA 기본동작을 확인한다")
//    public void testSpringDataJpa() throws Exception {
//        // given
//        Member member = new Member("test", "test", "test", 11);
//        memberRepository.save(member);
//
//        Board board = new Board(member, "title", "content");
//        boardRepository.save(board);
//
//        // when
//        List<Board> boardList = boardRepository.findByMember(member);
//
//        // then
//        assertThat(boardList).extracting("member").containsOnly(member);
//        assertEquals("title", boardList.get(0).getTitle());
//    }
}