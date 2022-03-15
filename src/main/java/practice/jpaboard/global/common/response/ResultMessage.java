package practice.jpaboard.global.common.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ResultMessage {
    private boolean success;
    private Object data;
    private HttpStatus statusCode;
    private String message;

    public final static ResultMessage of(final boolean success, final Object data, final HttpStatus statusCode, final String message) {
        return ResultMessage.builder()
                .success(success)
                .data(data)
                .statusCode(statusCode)
                .message(message)
                .build();
    }

    public final static ResultMessage of(final boolean success, final Object data, final HttpStatus statusCode) {
        return ResultMessage.builder()
                .success(success)
                .data(data)
                .statusCode(statusCode)
                .build();
    }

    public final static ResultMessage of(final boolean success, final HttpStatus statusCode, final String message) {
        return ResultMessage.builder()
                .success(success)
                .statusCode(statusCode)
                .message(message)
                .build();
    }

    public final static ResultMessage of(final boolean success, final HttpStatus statusCode) {
        return ResultMessage.builder()
                .success(success)
                .statusCode(statusCode)
                .build();
    }
}
