package practice.jpaboard.service;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.jpaboard.common.config.ResultMessage;
import practice.jpaboard.common.exception.board.BoardException;
import practice.jpaboard.common.exception.board.BoardNotFoundException;
import practice.jpaboard.dto.BoardDto;
import practice.jpaboard.dto.commentDto;
import practice.jpaboard.entity.Board;
import practice.jpaboard.entity.Comment;
import practice.jpaboard.entity.Like;
import practice.jpaboard.entity.Member;
import practice.jpaboard.repository.BoardRepository;
import practice.jpaboard.repository.CommentRepository;
import practice.jpaboard.repository.LikeRepository;
import practice.jpaboard.repository.MemberRepository;

import javax.servlet.http.HttpServletRequest;

@Service
@Transactional(readOnly = true)
public class BoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final CommonService commonService;

    public BoardService(MemberRepository memberRepository, BoardRepository boardRepository, LikeRepository likeRepository, CommentRepository commentRepository, CommonService commonService) {
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
        this.commonService = commonService;
    }

    @Transactional
    public ResultMessage upload(BoardDto boardDTO) {
        Board board;
        Member member = commonService.findUserIdFromAuth();

        try {
            board = boardRepository.save(boardDTO.toEntity(member));
        } catch (Exception e) {
            throw new BoardException("게시글 저장을 실패하였습니다.");
        }

        BoardDto result = boardRepository.findBoardDTOByNo(board.getNo()).orElseThrow(
                () -> new BoardNotFoundException());

        return ResultMessage.of(true, result, HttpStatus.OK);
    }

    public ResultMessage detail(Long no) {
        BoardDto result = boardRepository.findBoardDTOByNo(no).orElseThrow(
                () -> new BoardNotFoundException());
        Member member = commonService.findUserIdFromAuth();

        result.setLike(likeRepository.existsByMemberNoAndBoardNo(member.getNo(), no));

        return ResultMessage.of(true, result, HttpStatus.OK);
    }

    @Transactional
    public ResultMessage like(HttpServletRequest request, Long no) {
        Member member = commonService.findUserIdFromAuth();
        Board board = boardRepository.findById(no).orElseThrow(
                () -> new BoardNotFoundException());

        Like like = new Like(member, board);
        try {
            if ((HttpMethod.POST.matches(request.getMethod()))) {
                likeRepository.save(like);
            } else {
                likeRepository.deleteByMemberNoAndBoardNo(member.getNo(), no);
            }
        } catch (Exception e) {
            throw new BoardException("좋아요 변경을 실패했습니다");
        }

        return ResultMessage.of(true, HttpStatus.OK);
    }

    @Transactional
    public ResultMessage comment(HttpServletRequest request, Long no, commentDto commentDto) {
        Member member = commonService.findUserIdFromAuth();
        Board board = boardRepository.findById(no).orElseThrow(
                () -> new BoardNotFoundException());

        try {
            if (HttpMethod.POST.matches(request.getMethod())) {
                Comment comment = new Comment(member, board, commentDto.getContent());
                if (commentDto.getParent() != null) comment.setParent(commentDto.getParent());
                commentRepository.save(comment);
            } else if (HttpMethod.PATCH.matches(request.getMethod())) {
                Comment comment = commentRepository.findById(commentDto.getCommentNo()).orElseThrow(
                        () -> new BoardException("댓글 조회에 실패했습니다."));
                comment.setContent(commentDto.getContent());
            } else {
                Comment comment = commentRepository.findById(commentDto.getCommentNo()).orElseThrow(
                        () -> new BoardException("댓글 조회에 실패했습니다."));
                comment.setDeleteyn("Y");
            }

        } catch (Exception e) {
            throw new BoardException("댓글 변경을 실패했습니다.");
        }

        return ResultMessage.of(true, HttpStatus.OK);
    }
}
