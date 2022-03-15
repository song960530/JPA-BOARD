package practice.jpaboard.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import practice.jpaboard.dto.BoardDto;
import practice.jpaboard.dto.QBoardDTO;

import java.util.Optional;

import static practice.jpaboard.entity.QBoard.board;
import static practice.jpaboard.entity.QMember.member;

public class BoardQueryRepositoryImpl implements BoardQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public BoardQueryRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Optional<BoardDto> findBoardDTOByNo(Long no) {
        BoardDto boardDTO = jpaQueryFactory
                .select(new QBoardDTO(
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
