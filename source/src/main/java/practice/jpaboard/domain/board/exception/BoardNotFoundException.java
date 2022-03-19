package practice.jpaboard.domain.board.exception;

public class BoardNotFoundException extends RuntimeException{
    public BoardNotFoundException() {
        super("게시글 조회에 실패했습니다");
    }
}
