package uttugseuja.lucklotteryserver.domain.credential.exception;

import uttugseuja.lucklotteryserver.global.error.exception.ErrorCode;
import uttugseuja.lucklotteryserver.global.error.exception.LuckLotteryException;

public class UserIdMismatchException extends LuckLotteryException {

    public static final LuckLotteryException EXCEPTION = new UserIdMismatchException();

    private UserIdMismatchException() {
        super(ErrorCode.MISMATCH_USER_OAUTH_ID);
    }


}
