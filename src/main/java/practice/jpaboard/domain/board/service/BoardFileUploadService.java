package practice.jpaboard.domain.board.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class BoardFileUploadService {
    @Value("${spring.servlet.multipart.location}")
    private String FILE_PATH;

    public void fileUpload(List<MultipartFile> fileList) {

        fileList.stream().forEach(file -> {
            File filePath = new File(FILE_PATH);
            File saveFile = new File(file.getOriginalFilename());

            if (!filePath.exists()) {
                filePath.mkdir();
            }

            try {
                file.transferTo(saveFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
