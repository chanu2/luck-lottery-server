package uttugseuja.lucklotteryserver.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_TOKEN(401, "토큰이 유효하지 않습니다."),
    EXPIRED_TOKEN(401, "토큰이 만료되었습니다."),

    /* 403 UNAUTHORIZED : 인증되지 않은 사용자 */
    REGISTER_EXPIRED_TOKEN(HttpStatus.FORBIDDEN.value(),"refreshToken expired."),

    /* 404 NOT_FOUND : Resource를 찾을 수 없음 */
    LOTTERY_ROUND_NOT_FOUND(404, "해당 회차의 로또 정보를 찾을 수 없습니다."),
    USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),
    WINNING_PENSION_LOTTERY_NOT_FOUND(404,  "연금 복권의 당첨 번호를 찾을수 없습니다"),

    /* 500 */
    INTERNAL_SERVER_ERROR(500,"서버 에러")
    ;

    private int status;
    private String reason;
}