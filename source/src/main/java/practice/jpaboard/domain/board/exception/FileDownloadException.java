package practice.jpaboard.domain.board.exception;

public class FileDownloadException extends RuntimeException {
    public FileDownloadException() {
        super("파일 다운로드에 실패하였습니다");
    }
}
