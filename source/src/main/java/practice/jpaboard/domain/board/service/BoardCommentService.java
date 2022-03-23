package practice.jpaboard.domain.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.jpaboard.domain.board.dto.CommentDto;
import practice.jpaboard.domain.board.entity.Board;
import practice.jpaboard.domain.board.entity.Comment;
import practice.jpaboard.domain.board.exception.BoardException;
import practice.jpaboard.domain.board.exception.BoardNotFoundException;
import practice.jpaboard.domain.board.repository.BoardRepository;
import practice.jpaboard.domain.board.repository.CommentRepository;
import practice.jpaboard.domain.member.entity.Member;
import practice.jpaboard.domain.member.repository.MemberRepository;
import practice.jpaboard.global.annotation.LoginCheck;
import practice.jpaboard.global.common.response.ResultMessage;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardCommentService {
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    @LoginCheck
    public ResultMessage saveComment(Long no, CommentDto commentDto) {
        Member member = findUserIdFromAuth();
        Board board = boardRepository.findById(no).orElseThrow(
                () -> new BoardNotFoundException());
        try {
            Comment comment = new Comment(member, board, commentDto.getContent());
            if (commentDto.getParent() != null) comment.setParent(commentDto.getParent());

            commentRepository.save(comment);
        } catch (Exception e) {
            throw new BoardException("댓글 저장을 실패했습니다.");
        }

        return ResultMessage.of(true, HttpStatus.OK);
    }

    @Transactional
    @LoginCheck
    public ResultMessage updateComment(CommentDto commentDto) {
        Member member = findUserIdFromAuth();

        try {
            Comment comment = commentRepository.findById(commentDto.getCommentNo()).orElseThrow(
                    () -> new BoardException("댓글 조회를 실패했습니다."));

            if (!comment.getMember().getUserId().equals(member.getUserId())) throw new Exception();

            comment.setContent(commentDto.getContent());
        } catch (Exception e) {
            throw new BoardException("댓글 변경을 실패했습니다.");
        }

        return ResultMessage.of(true, HttpStatus.OK);
    }

    @Transactional
    @LoginCheck
    public ResultMessage deleteComment(CommentDto commentDto) {
        Member member = findUserIdFromAuth();

        try {
            Comment comment = commentRepository.findById(commentDto.getCommentNo()).orElseThrow(
                    () -> new BoardException("댓글 조회를 실패했습니다."));

            if (!comment.getMember().getUserId().equals(member.getUserId())) throw new Exception();

            comment.setContent(commentDto.getContent());
        } catch (Exception e) {
            throw new BoardException("댓글 삭제를 실패했습니다.");
        }

        return ResultMessage.of(true, HttpStatus.OK);
    }

    public Member findUserIdFromAuth() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return memberRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("가입되지 않은 ID입니다"));
    }
}
