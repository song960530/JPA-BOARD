package practice.jpaboard.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.jpaboard.common.config.ResultMessage;
import practice.jpaboard.common.exception.board.BoardException;
import practice.jpaboard.dto.BoardDto;
import practice.jpaboard.entity.Board;
import practice.jpaboard.entity.Like;
import practice.jpaboard.entity.Member;
import practice.jpaboard.repository.BoardRepository;
import practice.jpaboard.repository.LikeRepository;
import practice.jpaboard.repository.MemberRepository;

import javax.servlet.http.HttpServletRequest;

@Service
@Transactional(readOnly = true)
public class BoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;
    private final CommonService commonService;

    public BoardService(MemberRepository memberRepository, BoardRepository boardRepository, LikeRepository likeRepository, CommonService commonService) {
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
        this.likeRepository = likeRepository;
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

        BoardDto result = boardRepository.findBoardDTOByNo(board.getNo())
                .orElseThrow(() -> new BoardException("게시물 조회에 실패했습니다"));

        return ResultMessage.of(true, result, HttpStatus.OK);
    }

    public ResultMessage detail(Long no) {
        BoardDto result = boardRepository.findBoardDTOByNo(no).orElseThrow(() -> new BoardException("게시물 조회에 실패했습니다"));
        Member member = commonService.findUserIdFromAuth();

        result.setLike(likeRepository.existsByMemberNoAndBoardNo(member.getNo(), no));

        return ResultMessage.of(true, result, HttpStatus.OK);
    }

    @Transactional
    public ResultMessage like(HttpServletRequest request, Long no) {
        Member member = commonService.findUserIdFromAuth();
        Board board = boardRepository.findById(no)
                .orElseThrow(() -> new BoardException("게시물 조회에 실패했습니다"));

        Like like = new Like(member, board);
        try {
            if (request.getMethod().equals("POST")) {
                likeRepository.save(like);
            } else {
                likeRepository.deleteByMemberNoAndBoardNo(member.getNo(), no);
            }
        } catch (Exception e) {
            throw new BoardException("좋아요 변경을 실패했습니다");
        }

        return ResultMessage.of(true, HttpStatus.OK);
    }

    public Object comment(Long no) {
        return null;
    }
}
