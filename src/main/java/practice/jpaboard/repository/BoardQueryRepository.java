package practice.jpaboard.repository;

import practice.jpaboard.dto.BoardDto;

import java.util.Optional;

public interface BoardQueryRepository {
    Optional<BoardDto> findBoardDTOByNo(Long no);

}
