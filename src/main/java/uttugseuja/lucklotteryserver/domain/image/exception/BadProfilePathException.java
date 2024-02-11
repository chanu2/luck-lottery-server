package uttugseuja.lucklotteryserver.domain.image.exception;

import uttugseuja.lucklotteryserver.global.error.exception.ErrorCode;
import uttugseuja.lucklotteryserver.global.error.exception.LuckLotteryException;

public class BadProfilePathException extends LuckLotteryException {

    public static final LuckLotteryException EXCEPTION = new BadProfilePathException();

    private BadProfilePathException() {
        super(ErrorCode.BAD_PROFILE_PATH);
    }
}
