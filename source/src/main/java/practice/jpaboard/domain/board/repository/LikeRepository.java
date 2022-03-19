package practice.jpaboard.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.jpaboard.domain.board.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Boolean existsByMemberNoAndBoardNo(Long memberNo, Long boardNo);

    void deleteByMemberNoAndBoardNo(Long memberNo, Long boardNo);
}
