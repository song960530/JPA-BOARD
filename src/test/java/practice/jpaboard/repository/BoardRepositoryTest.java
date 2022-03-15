package practice.jpaboard.repository;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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