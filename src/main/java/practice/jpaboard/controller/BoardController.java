package practice.jpaboard.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.jpaboard.common.config.ResultMessage;
import practice.jpaboard.dto.BoardDto;
import practice.jpaboard.dto.commentDto;
import practice.jpaboard.service.BoardService;

import javax.servlet.http.HttpServletRequest;

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

    @PostMapping("/board")
    public ResponseEntity<ResultMessage> upload(@RequestBody BoardDto boardDTO) {
        return new ResponseEntity<>(boardService.upload(boardDTO), header, HttpStatus.OK);
    }

    @GetMapping("/board/{no}")
    public ResponseEntity<ResultMessage> detail(@PathVariable Long no) {
        return new ResponseEntity<>(boardService.detail(no), header, HttpStatus.OK);
    }

    @RequestMapping(value = "/board/{no}/like", method = {RequestMethod.POST, RequestMethod.DELETE})
    public ResponseEntity<ResultMessage> like(HttpServletRequest request, @PathVariable Long no) {
        return new ResponseEntity<>(boardService.like(request, no), header, HttpStatus.OK);
    }

    @RequestMapping(value = "/board/{no}/comment", method = {RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PATCH})
    public ResponseEntity<ResultMessage> comment(HttpServletRequest request, @PathVariable Long no, @RequestBody commentDto commentDto) {
        return new ResponseEntity<>(boardService.comment(request, no, commentDto), header, HttpStatus.OK);
    }
}
