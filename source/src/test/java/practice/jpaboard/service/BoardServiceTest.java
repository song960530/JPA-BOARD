package practice.jpaboard.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import practice.jpaboard.domain.board.dto.BoardDto;
import practice.jpaboard.domain.board.dto.commentDto;
import practice.jpaboard.domain.board.entity.Board;
import practice.jpaboard.domain.board.entity.Comment;
import practice.jpaboard.domain.board.entity.Like;
import practice.jpaboard.domain.board.exception.BoardException;
import practice.jpaboard.domain.board.exception.BoardNotFoundException;
import practice.jpaboard.domain.board.repository.BoardRepository;
import practice.jpaboard.domain.board.repository.CommentRepository;
import practice.jpaboard.domain.board.repository.LikeRepository;
import practice.jpaboard.domain.board.service.BoardFileService;
import practice.jpaboard.domain.board.service.BoardService;
import practice.jpaboard.domain.member.entity.Member;
import practice.jpaboard.domain.member.repository.MemberRepository;
import practice.jpaboard.domain.member.service.MemberService;
import practice.jpaboard.global.common.response.ResultMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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
    @Mock
    private BoardFileService boardFileService;

    @BeforeEach
    void setup() {
        boardService = new BoardService(memberRepository, boardRepository, likeRepository, commentRepository, memberService, boardFileService);
    }

    BoardDto getBoardDto() {
        return BoardDto.builder()
                .userId("test")
                .title("test")
                .content("content")
                .regDt("20220314000000")
                .modiDt("20220314000000")
                .build();
    }

    Member getMember() {
        return new Member("test", "test", "test", 20);
    }

    Board getBoard() {
        return new Board(getMember(), "test", "content");
    }

    Like getLike() {
        return new Like(getMember(), getBoard());
    }

    Comment getComment() {
        return new Comment(getMember(), getBoard(), "content");

    }

    @Test
    @DisplayName("가입되지않은ID 오류가 떠야한다")
    public void illegalargExceptionTest() throws Exception {
        // given
        BoardDto boardDto = getBoardDto();

        // when
        doThrow(IllegalArgumentException.class).when(memberService).findUserIdFromAuth();

        // then
        assertThrows(IllegalArgumentException.class, () -> {
            boardService.upload(boardDto, null);
        });
    }

    @Test
    @DisplayName("게시글 저장도중 오류가 발생한다")
    @WithMockUser(username = "test", password = "test", roles = {"USER"})
    public void saveException() throws Exception {
        // given
        BoardDto boardDto = getBoardDto();
        Member member = getMember();

        // when
        doReturn(member).when(memberService).findUserIdFromAuth();
        doReturn(Exception.class).when(boardRepository).save(any(Board.class));

        // then
        assertThrows(BoardException.class, () -> {
            boardService.upload(boardDto, null);
        });
    }

    @Test
    @DisplayName("게시글 조회를 실패한다")
    @WithMockUser(username = "test", password = "test", roles = {"USER"})
    public void failFindBoard() throws Exception {
        // given
        BoardDto boardDto = getBoardDto();
        Member member = getMember();
        Board board = getBoard();

        // when
        doReturn(member).when(memberService).findUserIdFromAuth();
        doReturn(board).when(boardRepository).save(any(Board.class));
        doReturn(Optional.empty()).when(boardRepository).findBoardDtoByNo(any());

        // then
        assertThrows(BoardNotFoundException.class, () -> {
            boardService.upload(boardDto, null);
        });
    }


    @Test
    @DisplayName("게시글 등록 성공")
    @WithMockUser(username = "test", password = "test", roles = {"USER"})
    public void successSaveBoard() throws Exception {
        // given
        BoardDto boardDto = getBoardDto();
        Member member = getMember();
        Board board = getBoard();

        // when
        doReturn(member).when(memberService).findUserIdFromAuth();
        doReturn(board).when(boardRepository).save(any(Board.class));
        doReturn(Optional.of(getBoardDto())).when(boardRepository).findBoardDtoByNo(any());
        ResultMessage result = boardService.upload(boardDto, null);

        // then
        assertEquals(true, result.isSuccess());
        assertNotNull(result.getData());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @DisplayName("게시글 상세조회 중 BoardNotFoundException 발생")
    public void 상세조회중익셉션발생() throws Exception {
        // given
        Long no = 1L;

        // when
        doReturn(Optional.empty()).when(boardRepository).findBoardDtoByNo(anyLong());

        // then
        assertThrows(BoardNotFoundException.class, () -> {
            boardService.detail(no);
        });
    }

    @Test
    @DisplayName("게시글 상세조회 성공")
    public void 게시글조회성공() throws Exception {
        // given
        Long no = 1L;
        BoardDto boardDto = getBoardDto();
        Member member = getMember();

        // when
        doReturn(Optional.of(boardDto)).when(boardRepository).findBoardDtoByNo(anyLong());
        doReturn(member).when(memberService).findUserIdFromAuth();
        doReturn(true).when(likeRepository).existsByMemberNoAndBoardNo(any(), anyLong());
        ResultMessage result = boardService.detail(no);

        // then
        assertEquals(true, result.isSuccess());
        assertNotNull(result.getData());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @DisplayName("게시글 상세조회 중 BoardNotFoundException 발생")
    public void 상세조회중익셉션발생2() throws Exception {
        // given
        Long no = 1L;
        Member member = getMember();
        HttpServletRequest request = new MockHttpServletRequest();

        // when
        doReturn(member).when(memberService).findUserIdFromAuth();
        doReturn(Optional.empty()).when(boardRepository).findById(anyLong());

        // then
        assertThrows(BoardNotFoundException.class, () -> {
            boardService.like(request, no);
        });
    }


    @Test
    @DisplayName("좋아요 성공")
    public void 좋아요성공() throws Exception {
        // given
        Long no = 1L;
        Member member = getMember();
        Board board = getBoard();
        Like like = getLike();
        HttpServletRequest request = new MockHttpServletRequest(HttpMethod.POST.name(), "");

        // when
        doReturn(member).when(memberService).findUserIdFromAuth();
        doReturn(Optional.of(board)).when(boardRepository).findById(anyLong());
        doReturn(like).when(likeRepository).save(any(Like.class));
        ResultMessage result = boardService.like(request, no);

        // then
        assertEquals(true, result.isSuccess());
        assertNull(result.getData());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @DisplayName("좋아요 취소 성공")
    public void 좋아요취소성공() throws Exception {
        // given
        Long no = 1L;
        Member member = getMember();
        Board board = getBoard();
        Like like = getLike();
        HttpServletRequest request = new MockHttpServletRequest(HttpMethod.DELETE.name(), "");

        // when
        doReturn(member).when(memberService).findUserIdFromAuth();
        doReturn(Optional.of(board)).when(boardRepository).findById(anyLong());
        doNothing().when(likeRepository).deleteByMemberNoAndBoardNo(any(), anyLong());
        ResultMessage result = boardService.like(request, no);

        // then
        assertEquals(true, result.isSuccess());
        assertNull(result.getData());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @DisplayName("좋아요 변경 중 BoardException 발생")
    public void 좋아요변경중BoardException발생() throws Exception {
        // given
        Long no = 1L;
        Member member = getMember();
        Board board = getBoard();
        HttpServletRequest request = new MockHttpServletRequest(HttpMethod.POST.name(), "");

        // when
        doReturn(member).when(memberService).findUserIdFromAuth();
        doReturn(Optional.of(board)).when(boardRepository).findById(anyLong());
        doReturn(Exception.class).when(likeRepository).save(any(Like.class));

        // then
        assertThrows(BoardException.class, () -> {
            boardService.like(request, no);
        });
    }

    @Test
    @DisplayName("댓글 등록 성공")
    public void 댓글등록성공() throws Exception {
        // given
        Long no = 1L;
        Member member = getMember();
        Board board = getBoard();
        Comment comment = getComment();
        commentDto commentDto = new commentDto();
        commentDto.setParent(2L);
        HttpServletRequest request = new MockHttpServletRequest(HttpMethod.POST.name(), "");

        // when
        doReturn(member).when(memberService).findUserIdFromAuth();
        doReturn(Optional.of(board)).when(boardRepository).findById(anyLong());
        doReturn(comment).when(commentRepository).save(any(Comment.class));
        ResultMessage result = boardService.comment(request, no, commentDto);

        // then
        assertEquals(true, result.isSuccess());
        assertNull(result.getData());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @DisplayName("댓글 수정 성공")
    public void 댓글수정성공() throws Exception {
        // given
        Long no = 1L;
        Member member = getMember();
        Board board = getBoard();
        Comment comment = getComment();
        commentDto commentDto = new commentDto();
        commentDto.setCommentNo(1L);
        HttpServletRequest request = new MockHttpServletRequest(HttpMethod.PATCH.name(), "");

        // when
        doReturn(member).when(memberService).findUserIdFromAuth();
        doReturn(Optional.of(board)).when(boardRepository).findById(anyLong());
        doReturn(Optional.of(comment)).when(commentRepository).findById(anyLong());
        ResultMessage result = boardService.comment(request, no, commentDto);

        // then
        assertEquals(true, result.isSuccess());
        assertNull(result.getData());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }


    @Test
    @DisplayName("댓글 삭제 성공")
    public void 댓글삭제성공() throws Exception {
        // given
        Long no = 1L;
        Member member = getMember();
        Board board = getBoard();
        Comment comment = getComment();
        commentDto commentDto = new commentDto();
        commentDto.setCommentNo(1L);
        HttpServletRequest request = new MockHttpServletRequest(HttpMethod.DELETE.name(), "");

        // when
        doReturn(member).when(memberService).findUserIdFromAuth();
        doReturn(Optional.of(board)).when(boardRepository).findById(anyLong());
        doReturn(Optional.of(comment)).when(commentRepository).findById(anyLong());
        ResultMessage result = boardService.comment(request, no, commentDto);

        // then
        assertEquals(true, result.isSuccess());
        assertNull(result.getData());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @DisplayName("게시글 상세조회 중 BoardNotFoundException 발생")
    public void 상세조회중익셉션발생3() throws Exception {
        // given
        Long no = 1L;
        Member member = getMember();

        // when
        doReturn(member).when(memberService).findUserIdFromAuth();
        doReturn(Optional.empty()).when(boardRepository).findById(anyLong());

        // then
        assertThrows(BoardNotFoundException.class, () -> {
            boardService.comment(null, no, null);
        });
    }

    @Test
    @DisplayName("댓글 수정 중 조회 실패")
    public void 댓글수정실패() throws Exception {
        // given
        Long no = 1L;
        Member member = getMember();
        Board board = getBoard();
        Comment comment = getComment();
        commentDto commentDto = new commentDto();
        commentDto.setCommentNo(1L);
        HttpServletRequest request = new MockHttpServletRequest(HttpMethod.PATCH.name(), "");

        // when
        doReturn(member).when(memberService).findUserIdFromAuth();
        doReturn(Optional.of(board)).when(boardRepository).findById(anyLong());
        doReturn(Optional.empty()).when(commentRepository).findById(anyLong());

        // then
        assertThrows(BoardException.class, () -> {
            boardService.comment(request, no, commentDto);
        });
    }

    @Test
    @DisplayName("댓글 삭제 중 조회 실패")
    public void 댓글삭제실패() throws Exception {
        // given
        Long no = 1L;
        Member member = getMember();
        Board board = getBoard();
        Comment comment = getComment();
        commentDto commentDto = new commentDto();
        commentDto.setCommentNo(1L);
        HttpServletRequest request = new MockHttpServletRequest(HttpMethod.DELETE.name(), "");

        // when
        doReturn(member).when(memberService).findUserIdFromAuth();
        doReturn(Optional.of(board)).when(boardRepository).findById(anyLong());
        doReturn(Optional.empty()).when(commentRepository).findById(anyLong());

        // then
        assertThrows(BoardException.class, () -> {
            boardService.comment(request, no, commentDto);
        });
    }

    @Test
    @DisplayName("댓글 메서드실행 중 전체 익셉션")
    public void 댓글전체익셉션() throws Exception {
        // given
        Long no = 1L;
        Member member = getMember();
        Board board = getBoard();

        // when
        doReturn(member).when(memberService).findUserIdFromAuth();
        doReturn(Optional.of(board)).when(boardRepository).findById(anyLong());

        // then
        assertThrows(Exception.class, () -> {
            boardService.comment(null, no, null);
        });
    }
}