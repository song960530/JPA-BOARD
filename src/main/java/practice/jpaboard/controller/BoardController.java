package practice.jpaboard.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import practice.jpaboard.common.config.ResultMessage;
import practice.jpaboard.dto.BoardDTO;
import practice.jpaboard.service.BoardService;

@RestController
public class BoardController {

    private final BoardService boardService;
    private final HttpHeaders header = Headers();

    private HttpHeaders Headers() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        return header;
    }

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/board/upload")
    public ResponseEntity<ResultMessage> upload(@RequestBody BoardDTO boardDTO) {
        return new ResponseEntity<>(boardService.upload(boardDTO), header, HttpStatus.OK);
    }
}
