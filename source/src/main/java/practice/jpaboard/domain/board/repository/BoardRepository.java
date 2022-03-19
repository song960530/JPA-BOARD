package practice.jpaboard.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.jpaboard.domain.board.entity.Board;
import practice.jpaboard.domain.member.entity.Member;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardQueryRepository {
    List<Board> findByMember(Member member);
}
