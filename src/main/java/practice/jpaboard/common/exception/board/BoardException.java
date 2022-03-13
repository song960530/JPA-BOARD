package practice.jpaboard.common.exception.board;

public class BoardException extends RuntimeException {

    public BoardException() {
    }

    public BoardException(String message) {
        super(message);
    }
}
