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
import practice.jpaboard.common.config.ResultMessage;
import practice.jpaboard.common.exception.board.BoardException;
import practice.jpaboard.dto.BoardDTO;
import practice.jpaboard.entity.Board;
import practice.jpaboard.entity.Member;
import practice.jpaboard.repository.BoardRepository;
import practice.jpaboard.repository.MemberRepository;

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

    @BeforeEach
    void setup() {
        boardService = new BoardService(memberRepository, boardRepository);
    }

    BoardDTO boardDTO() {
        return BoardDTO.builder()
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
        BoardDTO boardDTO = boardDTO();

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
        BoardDTO boardDTO = boardDTO();
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
        BoardDTO boardDTO = boardDTO();
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
        BoardDTO boardDTO = boardDTO();
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