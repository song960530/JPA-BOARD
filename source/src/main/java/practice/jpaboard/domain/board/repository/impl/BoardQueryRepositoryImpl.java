package practice.jpaboard.domain.board.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import practice.jpaboard.domain.board.dto.BoardDto;
import practice.jpaboard.domain.board.dto.QBoardDto;
import practice.jpaboard.domain.board.repository.BoardQueryRepository;

import java.util.Optional;

import static practice.jpaboard.domain.board.entity.QBoard.board;
import static practice.jpaboard.domain.member.entity.QMember.member;


public class BoardQueryRepositoryImpl implements BoardQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public BoardQueryRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Optional<BoardDto> findBoardDtoByNo(Long no) {
        BoardDto boardDTO = jpaQueryFactory
                .select(new QBoardDto(
                        board.no
                        , member.userId
                        , board.title
                        , board.content
                        , board.regDt
                        , board.modiDt
                ))
                .from(board)
                .join(board.member, member)
                .where(board.no.eq(no))
                .fetchOne();

        return Optional.ofNullable(boardDTO);
    }
}
