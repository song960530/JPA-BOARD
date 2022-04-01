package practice.jpaboard.domain.board.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import practice.jpaboard.domain.board.dto.BoardDto;
import practice.jpaboard.domain.board.dto.CommentDto;
import practice.jpaboard.domain.board.service.BoardCommentService;
import practice.jpaboard.domain.board.service.BoardService;
import practice.jpaboard.global.common.response.ResultMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class BoardController {

    private final BoardService boardService;
    private final BoardCommentService commentService;
    private final HttpHeaders header = Headers();

    private HttpHeaders Headers() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        return header;
    }

    public BoardController(BoardService boardService, BoardCommentService commentService) {
        this.boardService = boardService;
        this.commentService = commentService;
    }

    @PostMapping("/board")
    public ResponseEntity<ResultMessage> upload(@RequestPart("dto") BoardDto boardDTO, @RequestPart(value = "file", required = false) List<MultipartFile> fileList) {
        return new ResponseEntity<>(boardService.upload(boardDTO, fileList), header, HttpStatus.OK);
    }

    @GetMapping("/board/{no}")
    public ResponseEntity<ResultMessage> detail(@PathVariable Long no) {
        return new ResponseEntity<>(boardService.detail(no), header, HttpStatus.OK);
    }

//    @GetMapping("/board/{no}/download/{encrtpyName}")
//    public ResponseEntity<ResultMessage> detail(@PathVariable Long no, @PathVariable String encrtpyName) {
//        return new ResponseEntity<>(boardService.detail(no), header, HttpStatus.OK);
//    }

    @RequestMapping(value = "/board/{no}/like", method = {RequestMethod.POST, RequestMethod.DELETE})
    public ResponseEntity<ResultMessage> like(HttpServletRequest request, @PathVariable Long no) {
        return new ResponseEntity<>(boardService.like(request, no), header, HttpStatus.OK);
    }

    @GetMapping("/board/{no}/comment")
    public ResponseEntity<ResultMessage> saerchComments(@PathVariable Long no, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return new ResponseEntity<>(commentService.searchComments(no, pageable), header, HttpStatus.OK);
    }

    @PostMapping("/board/{no}/comment")
    public ResponseEntity<ResultMessage> saveComment(@PathVariable Long no, @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.saveComment(no, commentDto), header, HttpStatus.OK);
    }

    @PatchMapping("/board/{no}/comment")
    public ResponseEntity<ResultMessage> updateComment(@PathVariable Long no, @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.updateComment(commentDto), header, HttpStatus.OK);
    }

    @DeleteMapping("/board/{no}/comment")
    public ResponseEntity<ResultMessage> deleteComment(@PathVariable Long no, @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.deleteComment(commentDto), header, HttpStatus.OK);
    }
}
