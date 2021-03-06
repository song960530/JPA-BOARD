package practice.jpaboard.domain.member.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import practice.jpaboard.global.common.response.ResultMessage;
import practice.jpaboard.domain.member.dto.JoinDto;
import practice.jpaboard.domain.member.dto.LoginDto;
import practice.jpaboard.domain.member.service.MemberService;

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
    public ResponseEntity<ResultMessage> join(@RequestBody JoinDto joinDto) {

        return new ResponseEntity<>(memberService.join(joinDto), header, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ResultMessage> login(@RequestBody LoginDto loginDto) {

        return new ResponseEntity<>(memberService.login(loginDto), header, HttpStatus.OK);
    }
}
