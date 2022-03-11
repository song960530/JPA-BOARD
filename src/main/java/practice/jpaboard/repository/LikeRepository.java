package practice.jpaboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.jpaboard.entity.Board;
import practice.jpaboard.entity.Like;
import practice.jpaboard.entity.Member;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Boolean existsByMemberAndBoard(Member member, Board board);
}
