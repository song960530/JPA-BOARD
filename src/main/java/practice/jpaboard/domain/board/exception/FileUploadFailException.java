package practice.jpaboard.domain.board.exception;

public class FileUploadFailException extends RuntimeException {
    public FileUploadFailException() {
        super("파일 저장에 실패하였습니다");
    }
}
