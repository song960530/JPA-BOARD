package practice.jpaboard.domain.member.exception;

public class LoginException extends RuntimeException {
    public LoginException() {
        super("로그인 정보가 올바르지 않습니다. 재로그인 후 다시 시도해주세요");
    }
}
