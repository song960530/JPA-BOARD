package practice.jpaboard.domain.board.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import practice.jpaboard.domain.board.dto.BoardDto;
import practice.jpaboard.domain.board.dto.CommentDto;
import practice.jpaboard.domain.board.entity.Board;
import practice.jpaboard.domain.board.entity.Comment;
import practice.jpaboard.domain.board.entity.Like;
import practice.jpaboard.domain.board.exception.BoardException;
import practice.jpaboard.domain.board.exception.BoardNotFoundException;
import practice.jpaboard.domain.board.repository.BoardRepository;
import practice.jpaboard.domain.board.repository.CommentRepository;
import practice.jpaboard.domain.board.repository.LikeRepository;
import practice.jpaboard.domain.member.entity.Member;
import practice.jpaboard.domain.member.repository.MemberRepository;
import practice.jpaboard.global.annotation.LoginCheck;
import practice.jpaboard.global.common.response.ResultMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Service Layer 간의 의존관계를 끊어내기 쉽지 않아
 * 각각 특성에 맞는 Service를 생성하고
 * BoardService 에서 각각의 서비스를 주입받아 사용한다.
 *
 * => Service Layer간의 의존관계는 BoardService 에서만 허용한다.
 */
@Service
@Transactional(readOnly = true)
public class BoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final BoardFileService boardFileService;

    public BoardService(MemberRepository memberRepository, BoardRepository boardRepository, LikeRepository likeRepository, CommentRepository commentRepository, BoardFileService boardFileService) {
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
        this.boardFileService = boardFileService;
    }

    @Transactional
    @LoginCheck
    public ResultMessage upload(BoardDto boardDTO, List<MultipartFile> fileList) {
        Board board;
        Member member = findUserIdFromAuth();

        try {
            board = boardRepository.save(boardDTO.toEntity(member));
        } catch (Exception e) {
            throw new BoardException("게시글 저장을 실패하였습니다.");
        }

        if (fileList != null && fileList.size() > 0) boardFileService.fileUpload(member, board, fileList); // 파일저장 호출

        BoardDto result = boardRepository.findBoardDtoByNo(board.getNo()).orElseThrow(
                () -> new BoardNotFoundException());

        return ResultMessage.of(true, result, HttpStatus.OK);
    }

    public ResultMessage detail(Long no) {
        BoardDto result = boardRepository.findBoardDtoByNo(no).orElseThrow(
                () -> new BoardNotFoundException());
        Member member = findUserIdFromAuth();
        // TODO : 디테일은 로그인 없어도 가능하게 바꾸자

        result.setLike(likeRepository.existsByMemberNoAndBoardNo(member.getNo(), no));

        result.setUploadList(boardFileService.uploadDtoList(no));

        return ResultMessage.of(true, result, HttpStatus.OK);
    }

    @Transactional
    @LoginCheck
    public ResultMessage like(HttpServletRequest request, Long no) {
        Member member = findUserIdFromAuth();
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

    public Member findUserIdFromAuth() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return memberRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("가입되지 않은 ID입니다"));
    }
}
