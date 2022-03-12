package practice.jpaboard.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import practice.jpaboard.common.config.ResultMessage;
import practice.jpaboard.dto.JoinDTO;
import practice.jpaboard.dto.LoginDTO;
import practice.jpaboard.service.MemberService;

@RestController
public class MemberController {

    private final MemberService memberService;
    private final HttpHeaders header = Headers();

    private HttpHeaders Headers() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        return header;
    }

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/join")
    public ResponseEntity<ResultMessage> join(@RequestBody JoinDTO joinDto) {

        return new ResponseEntity<>(memberService.join(joinDto), header, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ResultMessage> login(@RequestBody LoginDTO loginDto) {

        return new ResponseEntity<>(memberService.login(loginDto), header, HttpStatus.OK);
    }
}
