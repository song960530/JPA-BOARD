package practice.jpaboard.domain.board.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import practice.jpaboard.domain.board.dto.CommentDto;
import practice.jpaboard.domain.board.dto.QCommentDto;
import practice.jpaboard.domain.board.repository.CommentQueryRepository;

import java.util.List;

import static practice.jpaboard.domain.board.entity.QBoard.board;
import static practice.jpaboard.domain.board.entity.QComment.comment;
import static practice.jpaboard.domain.member.entity.QMember.member;

@Repository
public class CommentQueryRepositoryImpl implements CommentQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public CommentQueryRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<CommentDto> findPageBoardDtoByNo(Long no, Pageable pageable) {
        QueryResults<CommentDto> result = jpaQueryFactory
                .select(new QCommentDto(
                        comment.no
                        , comment.board.no
                        , comment.member.userId
                        , comment.parent
                        , comment.content
                ))
                .from(comment)
                .join(comment.member, member)
                .join(comment.board, board)
                .where(
                        boardNoEq(no)
                        , parentEq(0L)
                )
                .orderBy(comment.no.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<CommentDto> contents = result.getResults();
        long total = result.getTotal();

        return new PageImpl<>(contents, pageable, total);
    }

    private BooleanExpression boardNoEq(Long boardNo) {
        return boardNo != null ? comment.board.no.eq(boardNo) : null;
    }

    private BooleanExpression parentEq(Long parent) {
        return parent != null ? comment.parent.eq(parent) : null;
    }
}
