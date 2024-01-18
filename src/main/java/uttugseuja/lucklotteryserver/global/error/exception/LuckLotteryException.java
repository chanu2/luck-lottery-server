package uttugseuja.lucklotteryserver.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LuckLotteryException extends RuntimeException{

    private ErrorCode errorCode;
}
