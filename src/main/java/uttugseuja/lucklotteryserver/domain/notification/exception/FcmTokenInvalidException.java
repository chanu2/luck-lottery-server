package uttugseuja.lucklotteryserver.domain.notification.exception;

import uttugseuja.lucklotteryserver.global.error.exception.ErrorCode;
import uttugseuja.lucklotteryserver.global.error.exception.LuckLotteryException;

public class FcmTokenInvalidException extends LuckLotteryException {

    public static final LuckLotteryException EXCEPTION = new FcmTokenInvalidException();

    public FcmTokenInvalidException() {
        super(ErrorCode.NOTIFICATION_FCM_TOKEN_INVALID);
    }
}
