package practice.jpaboard.domain.board.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import practice.jpaboard.domain.board.entity.Board;
import practice.jpaboard.domain.board.entity.Upload;
import practice.jpaboard.domain.board.exception.FileUploadFailException;
import practice.jpaboard.domain.board.repository.UploadRepository;
import practice.jpaboard.domain.member.entity.Member;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class BoardFileUploadService {
    @Value("${spring.servlet.multipart.location}")
    private String FILE_PATH;
    private final UploadRepository uploadRepository;

    public BoardFileUploadService(UploadRepository uploadRepository) {
        this.uploadRepository = uploadRepository;
    }

    @Transactional
    public void fileUpload(Member member, Board board, List<MultipartFile> fileList) {
        fileList.stream().forEach(file -> {
            Upload upload = null;
            File filePath = new File(FILE_PATH);
            File saveFile = new File(MD5Generator(file.getOriginalFilename())); // 파일이름 + UUID로 MD5 생성
//            String ext = FilenameUtils.getExtension(file.getOriginalFilename());

            if (!filePath.exists()) {
                filePath.mkdirs(); // 디렉토리가 없으면 생성
            }

            try {
                file.transferTo(saveFile); // 저장경로에 파일 저장
            } catch (IOException e) {
                throw new FileUploadFailException();
            }

            upload = new Upload(member, board, file.getOriginalFilename(), saveFile.getName());
            uploadRepository.save(upload);
        });
    }

    public static String MD5Generator(String fileName) {
        MessageDigest mdMD5 = null;
        StringBuilder hexMD5hash = new StringBuilder();
        String fileWithUUID = fileName + UUID.randomUUID().toString();
        try {
            mdMD5 = MessageDigest.getInstance("MD5");
            mdMD5.update(fileWithUUID.getBytes("UTF-8"));
            byte[] md5Hash = mdMD5.digest();
            for (byte b : md5Hash) {
                String hexString = String.format("%02x", b);
                hexMD5hash.append(hexString);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return hexMD5hash.toString();
    }
}
