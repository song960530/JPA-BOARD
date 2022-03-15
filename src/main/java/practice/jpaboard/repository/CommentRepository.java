package practice.jpaboard.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import practice.jpaboard.entity.Board;
import practice.jpaboard.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoard(Board board);

    @Override
    @EntityGraph(attributePaths = {"member"})
    Optional<Comment> findById(Long no);
}
