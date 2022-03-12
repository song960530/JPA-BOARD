package practice.jpaboard.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import practice.jpaboard.common.config.ResultMessage;
import practice.jpaboard.dto.JoinDTO;
import practice.jpaboard.dto.LoginDTO;
import practice.jpaboard.service.MemberService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MemberControllerTest {

    @InjectMocks
    private MemberController memberController;

    @Mock
    private MemberService memberService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }

    private JoinDTO joinDTO() {
        return JoinDTO.builder()
                .userId("test")
                .password("패스워드")
                .age(20)
                .name("이름")
                .build();
    }

    @Test
    @DisplayName("회원가입 호출")
    public void join() throws Exception {
        // given
        JoinDTO joinDTO = joinDTO();
        doReturn(ResultMessage.of(true, HttpStatus.OK)).when(memberService).join(any(JoinDTO.class));

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(joinDTO))
        );

        // then
        String response = resultActions.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        ResultMessage resultMessage = new Gson().fromJson(response, ResultMessage.class);
        assertEquals(true, resultMessage.isSuccess());
        assertEquals(HttpStatus.OK, resultMessage.getStatusCode());
    }

    @Test
    @DisplayName("로그인 호출")
    public void login() throws Exception {
        // given
        doReturn(ResultMessage.of(true, null, HttpStatus.OK)).when(memberService).login(any(LoginDTO.class));

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(new LoginDTO()))
        );

        // then
        String response = resultActions.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        ResultMessage resultMessage = new Gson().fromJson(response, ResultMessage.class);
        assertEquals(true, resultMessage.isSuccess());
        assertEquals(HttpStatus.OK, resultMessage.getStatusCode());
    }


}