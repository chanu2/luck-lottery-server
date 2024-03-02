package uttugseuja.lucklotteryserver.domain.credential.exception;

import uttugseuja.lucklotteryserver.global.error.exception.ErrorCode;
import uttugseuja.lucklotteryserver.global.error.exception.LuckLotteryException;

public class NoSuchPublicKeyException extends LuckLotteryException {
    public static final LuckLotteryException EXCEPTION = new NoSuchPublicKeyException();

    private NoSuchPublicKeyException() {
        super(ErrorCode.NO_SUCH_PUBLIC_KEY);
    }
}
