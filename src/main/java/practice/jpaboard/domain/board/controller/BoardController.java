package practice.jpaboard.domain.board.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import practice.jpaboard.domain.board.dto.BoardDto;
import practice.jpaboard.domain.board.dto.commentDto;
import practice.jpaboard.domain.board.service.BoardFileUploadService;
import practice.jpaboard.domain.board.service.BoardService;
import practice.jpaboard.global.common.response.ResultMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class BoardController {

    private final BoardService boardService;
    private final BoardFileUploadService boardFileUploadService;
    private final HttpHeaders header = Headers();

    private HttpHeaders Headers() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        return header;
    }

    public BoardController(BoardService boardService, BoardFileUploadService boardFileUploadService) {
        this.boardService = boardService;
        this.boardFileUploadService = boardFileUploadService;
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

    @RequestMapping(value = "/upload", method = {RequestMethod.POST})
    public void test(List<MultipartFile> fileList) {
        boardFileUploadService.fileUpload(fileList);
    }
}