package practice.jpaboard.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import practice.jpaboard.entity.Board;
import practice.jpaboard.entity.Like;
import practice.jpaboard.entity.Member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LikeRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    LikeRepository likeRepository;

    @Test
    @DisplayName("스프링데이터JPA 기본동작을 확인한다")
    public void testSpringDataJpa() throws Exception {
        // given
        Member member = new Member("test", "test", "test", 11);
        memberRepository.save(member);

        Board board = new Board(member, "title", "content");
        boardRepository.save(board);

        Like like = new Like(member, board);
        likeRepository.save(like);

        // when
        Boolean isLike = likeRepository.existsByMemberAndBoard(member, board);

        // then
        assertEquals(true, isLike);
    }
}