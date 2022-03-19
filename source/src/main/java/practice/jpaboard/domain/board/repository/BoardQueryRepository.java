package practice.jpaboard.domain.board.repository;

import practice.jpaboard.domain.board.dto.BoardDto;

import java.util.Optional;

public interface BoardQueryRepository {
    Optional<BoardDto> findBoardDtoByNo(Long no);

}
