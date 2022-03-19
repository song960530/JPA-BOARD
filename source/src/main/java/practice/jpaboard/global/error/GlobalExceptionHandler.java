package practice.jpaboard.global.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import practice.jpaboard.domain.board.exception.FileUploadFailException;
import practice.jpaboard.global.common.response.ResultMessage;
import practice.jpaboard.domain.board.exception.BoardException;
import practice.jpaboard.domain.board.exception.BoardNotFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultMessage> exception(Exception e) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        log.error(e.getMessage(), e);

        return new ResponseEntity<>(ResultMessage.of(false, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()), header, HttpStatus.OK);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResultMessage> illegalArgumentException(IllegalArgumentException e) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        log.error(e.getMessage());

        return new ResponseEntity<>(ResultMessage.of(false, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()), header, HttpStatus.OK);
    }

    @ExceptionHandler(BoardException.class)
    public ResponseEntity<ResultMessage> boardException(BoardException e) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        log.error(e.getMessage());

        return new ResponseEntity<>(ResultMessage.of(false, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()), header, HttpStatus.OK);
    }

    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseEntity<ResultMessage> boardNotFoundException(BoardNotFoundException e) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        log.error(e.getMessage());

        return new ResponseEntity<>(ResultMessage.of(false, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()), header, HttpStatus.OK);
    }

    @ExceptionHandler(FileUploadFailException.class)
    public ResponseEntity<ResultMessage> fileUploadException(FileUploadFailException e) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        log.error(e.getMessage());

        return new ResponseEntity<>(ResultMessage.of(false, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()), header, HttpStatus.OK);
    }
}
