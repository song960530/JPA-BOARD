package practice.jpaboard.domain.board.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import practice.jpaboard.domain.board.repository.UploadRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class BoardFileServiceTest {

    private  BoardFileService boardFileService;
    @Mock
    private UploadRepository uploadRepository;

    @BeforeEach
    void setup() {
        this.boardFileService = new BoardFileService(uploadRepository);
    }
}