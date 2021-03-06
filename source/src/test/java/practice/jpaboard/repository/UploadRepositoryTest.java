package practice.jpaboard.repository;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UploadRepositoryTest {
//    @Autowired
//    MemberRepository memberRepository;
//
//    @Autowired
//    BoardRepository boardRepository;
//
//    @Autowired
//    UploadRepository uploadRepository;
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
//        Upload upload = new Upload(member, board, "filename");
//        uploadRepository.save(upload);
//
//        // when
//        List<Upload> uploadList = uploadRepository.findByBoard(board);
//
//        // then
//        assertThat(uploadList).extracting("member").containsOnly(member);
//        assertThat(uploadList).extracting("board").containsOnly(board);
//        assertThat(uploadList).extracting("originName").containsOnly("filename");
//    }

}