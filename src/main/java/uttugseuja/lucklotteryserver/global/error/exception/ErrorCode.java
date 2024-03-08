package uttugseuja.lucklotteryserver.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    NOTIFICATION_FCM_TOKEN_INVALID(400, "FCM Token이 유효하지 않습니다."),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_TOKEN(401, "토큰이 유효하지 않습니다."),
    EXPIRED_TOKEN(401, "토큰이 만료되었습니다."),

    /* 403 UNAUTHORIZED : 인증되지 않은 사용자 */
    REGISTER_EXPIRED_TOKEN(HttpStatus.FORBIDDEN.value(),"refreshToken expired."),

    /* 404 NOT_FOUND : Resource를 찾을 수 없음 */
    LOTTERY_ROUND_NOT_FOUND(404, "해당 회차의 로또 정보를 찾을 수 없습니다."),
    USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),
    WINNING_PENSION_LOTTERY_NOT_FOUND(404,  "연금 복권의 당첨 번호를 찾을수 없습니다"),
    OVER_LOTTERY_ROUND(404, "해당 회차는 최대 회차 범위를 초과 했습니다."),
    BAD_FILE_EXTENSION(404,  "FILE extension error"),
    FILE_EMPTY(404,  "FILE empty"),
    FILE_UPLOAD_FAIL(404,  "FILE upload fail"),
    FILE_OVER_SIZE(404,  "FILE 크기가 10mb를 초과 하였습니다"),
    PROFILE_IMAGE_NOT_FOUND(404,  "프로필 이미지를 찾을 수 없습니다"),
    BAD_PROFILE_PATH(404,  "profile path error"),
    DUPLICATE_NUMBER(404, "숫자가 중복 되었습니다."),

    /* 500 */
    INTERNAL_SERVER_ERROR(500,"서버 에러"),

    /* 500 Internal Server Error: IO 관련 에러 */
    PAGE_ACCESS_LOTTERY_ERROR(500, "복권 사이트 페이지에 접속할 수 없습니다. URL 또는 네트워크 상태를 확인해 주세요"),
    CRAWLING_EXCEPTION(500, "크롤링중 입출력 작업 중 에러 발생"),
    DATA_NOT_FOUND(500, "크롤링으로 가져온 데이터가 Null 입니다"),
    INVALID_FIREBASE_KEY(500, "유효하지 않은 FireBase Key 입니다."),
    NO_SUCH_PUBLIC_KEY(500, " kid 값이 일치하는 데이터가 없습니다."),
    NO_SUCH_RSA_ALGORITHM(500, "RSA 암호화 알고리즘을 지원하지 않는 환경입니다."),
    WINNING_LOTTERY_NOT_FOUND(500, "해당 회차의 당첨 로또를 찾을 수 없습니다.")
    ;

    private int status;
    private String reason;
}