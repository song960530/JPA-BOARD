package practice.jpaboard.repository;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CommentRepositoryTest {

//    @Autowired
//    MemberRepository memberRepository;
//
//    @Autowired
//    BoardRepository boardRepository;
//
//    @Autowired
//    CommentRepository commentRepository;
//
//    @Autowired
//    EntityManager em;
//
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
//        Comment comment = new Comment(member, board, "content");
//        commentRepository.save(comment);
//
//        // when
//        List<Comment> commentList = commentRepository.findByBoard(board);
//
//        // then
//        assertThat(commentList).extracting("member").containsOnly(member);
//        assertThat(commentList).extracting("board").containsOnly(board);
//        assertThat(commentList).extracting("content").containsOnly("content");
//    }
}
