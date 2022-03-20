package practice.jpaboard.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.jpaboard.domain.board.entity.Board;
import practice.jpaboard.domain.board.entity.Upload;

import java.util.List;

public interface UploadRepository extends JpaRepository<Upload, Long> {
    List<Upload> findByBoard_No(Long no);
}
