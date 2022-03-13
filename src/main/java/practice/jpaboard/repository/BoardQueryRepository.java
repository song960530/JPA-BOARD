package practice.jpaboard.repository;

import practice.jpaboard.dto.BoardDTO;

import java.util.Optional;

public interface BoardQueryRepository {
    Optional<BoardDTO> findBoardDTOByNo(Long no);

}
