package uttugseuja.lucklotteryserver.global.exception;

import uttugseuja.lucklotteryserver.global.error.exception.ErrorCode;
import uttugseuja.lucklotteryserver.global.error.exception.LuckLotteryException;

public class InvalidFirebaseKeyException extends LuckLotteryException {

    public static final LuckLotteryException EXCEPTION = new InvalidFirebaseKeyException();

    private InvalidFirebaseKeyException() {
        super(ErrorCode.INVALID_FIREBASE_KEY);
    }
}
