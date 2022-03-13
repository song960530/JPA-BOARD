package practice.jpaboard.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.jpaboard.common.config.ResultMessage;
import practice.jpaboard.common.exception.board.BoardException;
import practice.jpaboard.dto.BoardDTO;
import practice.jpaboard.entity.Board;
import practice.jpaboard.entity.Member;
import practice.jpaboard.repository.BoardRepository;
import practice.jpaboard.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
public class BoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public BoardService(MemberRepository memberRepository, BoardRepository boardRepository) {
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
    }

    @Transactional
    public ResultMessage upload(BoardDTO boardDTO) {
        Board board;
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 ID입니다"));

        try {
            board = boardRepository.save(boardDTO.toEntity(member));
        } catch (Exception e) {
            throw new BoardException("게시글 저장을 실패하였습니다.");
        }

        BoardDTO result = boardRepository.findBoardDTOByNo(board.getNo())
                .orElseThrow(() -> new BoardException("게시물 조회에 실패했습니다"));

        return ResultMessage.of(true, result, HttpStatus.OK);
    }
}
