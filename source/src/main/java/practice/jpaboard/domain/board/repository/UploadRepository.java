package practice.jpaboard.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.jpaboard.domain.board.entity.Board;
import practice.jpaboard.domain.board.entity.Upload;

import java.util.List;
import java.util.Optional;

public interface UploadRepository extends JpaRepository<Upload, Long> {
    List<Upload> findByBoard_No(Long no);

    Optional<Upload> findByBoard_NoAndEncryptName(Long no, String encryptName);
}
