package practice.jpaboard.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import practice.jpaboard.domain.board.service.BoardService;
import practice.jpaboard.domain.member.service.MemberService;
import practice.jpaboard.global.common.response.ResultMessage;
import practice.jpaboard.domain.board.exception.BoardException;
import practice.jpaboard.domain.board.dto.BoardDto;
import practice.jpaboard.domain.board.entity.Board;
import practice.jpaboard.domain.member.entity.Member;
import practice.jpaboard.domain.board.repository.BoardRepository;
import practice.jpaboard.domain.board.repository.CommentRepository;
import practice.jpaboard.domain.board.repository.LikeRepository;
import practice.jpaboard.domain.member.repository.MemberRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class BoardServiceTest {

    private BoardService boardService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private BoardRepository boardRepository;
    @Mock
    private LikeRepository likeRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private MemberService memberService;

    @BeforeEach
    void setup() {
        boardService = new BoardService(memberRepository, boardRepository, likeRepository, commentRepository, memberService);
    }

    BoardDto boardDTO() {
        return BoardDto.builder()
                .userId("test")
                .title("test")
                .content("content")
                .regDt("20220314000000")
                .modiDt("20220314000000")
                .build();
    }

    @Test
    @DisplayName("가입되지않은ID 오류가 떠야한다")
    @WithMockUser(username = "test", password = "test", roles = {"USER"})
    public void illegalargExceptionTest() throws Exception {
        // given
        BoardDto boardDTO = boardDTO();

        // when
        doReturn(Optional.empty()).when(memberRepository).findByUserId(anyString());

        // then
        assertThrows(IllegalArgumentException.class, () -> {
            boardService.upload(boardDTO);
        });
    }

    @Test
    @DisplayName("게시글 저장도중 오류가 발생한다")
    @WithMockUser(username = "test", password = "test", roles = {"USER"})
    public void saveException() throws Exception {
        // given
        BoardDto boardDTO = boardDTO();
        Member member = new Member("test", "test", "test", 20);

        // when
        doReturn(Optional.of(member)).when(memberRepository).findByUserId(anyString());
        doReturn(Exception.class).when(boardRepository).save(any(Board.class));

        // then
        assertThrows(BoardException.class, () -> {
            boardService.upload(boardDTO);
        });
    }

    @Test
    @DisplayName("게시글 조회를 실패한다")
    @WithMockUser(username = "test", password = "test", roles = {"USER"})
    public void failFindBoard() throws Exception {
        // given
        BoardDto boardDTO = boardDTO();
        Member member = new Member("test", "test", "test", 20);

        // when
        doReturn(Optional.of(member)).when(memberRepository).findByUserId(anyString());
        doReturn(new Board(member, "test", "content")).when(boardRepository).save(any(Board.class));
        doReturn(Optional.empty()).when(boardRepository).findBoardDTOByNo(any());

        // then
        assertThrows(BoardException.class, () -> {
            boardService.upload(boardDTO);
        });
    }


    @Test
    @DisplayName("게시글 등록 성공")
    @WithMockUser(username = "test", password = "test", roles = {"USER"})
    public void successSaveBoard() throws Exception {
        // given
        BoardDto boardDTO = boardDTO();
        Member member = new Member("test", "test", "test", 20);

        // when
        doReturn(Optional.of(member)).when(memberRepository).findByUserId(anyString());
        doReturn(new Board(member, "test", "content")).when(boardRepository).save(any(Board.class));
        doReturn(Optional.of(boardDTO())).when(boardRepository).findBoardDTOByNo(any());
        ResultMessage result = boardService.upload(boardDTO);

        // then
        assertEquals(true, result.isSuccess());
        assertNotNull(result.getData());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}