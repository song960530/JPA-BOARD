package practice.jpaboard.domain.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import practice.jpaboard.domain.board.dto.CommentDto;

public interface CommentQueryRepository {

    Page<CommentDto> findPageBoardDtoByNo(Long no, Pageable pageable);
}
