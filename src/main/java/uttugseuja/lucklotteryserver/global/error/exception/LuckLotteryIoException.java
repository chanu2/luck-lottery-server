package uttugseuja.lucklotteryserver.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;

@Getter
@AllArgsConstructor
public class LuckLotteryIoException extends IOException {
    private ErrorCode errorCode;
}
