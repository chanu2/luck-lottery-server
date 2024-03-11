package uttugseuja.lucklotteryserver.domain.credential.exception;

import uttugseuja.lucklotteryserver.global.error.exception.ErrorCode;
import uttugseuja.lucklotteryserver.global.error.exception.LuckLotteryException;

public class NotNullTokenException extends LuckLotteryException {

    public static final LuckLotteryException EXCEPTION = new NotNullTokenException();

    private NotNullTokenException() {
        super(ErrorCode.NOT_NULL_TOKEN);
    }


}
