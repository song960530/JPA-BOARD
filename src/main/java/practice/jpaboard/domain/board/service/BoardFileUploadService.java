package practice.jpaboard.domain.board.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class BoardFileUploadService {
//    @Value("${my.filePath}")
//    private String FILE_PATH;

    public void fileUpload(List<MultipartFile> fileList) {

        fileList.stream().forEach(file -> {
            File saveFile = new File(file.getOriginalFilename());

//            if (!saveFile.getParentFile().exists()) {
//                saveFile.getParentFile().mkdir();
//            }

            try {
                file.transferTo(saveFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

//    public void fileUpload2(BoardDto boardDto) {
//        MultipartHttpServletRequest uploadFile = boardDto.getUploadFile();
//
//        try {
//            uploadFile.setCharacterEncoding(StandardCharsets.UTF_8.name());
//            Iterator<String> fileNames = uploadFile.getFileNames();
//
//            while (fileNames.hasNext()) {
//                Map<String, MultipartFile> fileMap = uploadFile.getFileMap();
//
//                uploadFile.getFileMap().entrySet().stream().forEach(
//                        entry -> {
//                            File file = new File(entry.getValue().getOriginalFilename());
//
//                            if (entry.getValue().getSize() != 0) {
//                                // 경로가 존재하지 않으면 생성
//                                if (!file.getParentFile().exists()) {
//                                    file.getParentFile().mkdir();
//                                }
//                                try {
//                                    entry.getValue().transferTo(file);
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        }
//                );
//            }
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }
}
