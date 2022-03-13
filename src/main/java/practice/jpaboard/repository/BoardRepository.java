package practice.jpaboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.jpaboard.entity.Board;
import practice.jpaboard.entity.Member;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardQueryRepository {
    List<Board> findByMember(Member member);
}
