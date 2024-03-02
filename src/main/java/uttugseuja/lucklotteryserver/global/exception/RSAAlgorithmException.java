package uttugseuja.lucklotteryserver.global.exception;

import uttugseuja.lucklotteryserver.global.error.exception.ErrorCode;
import uttugseuja.lucklotteryserver.global.error.exception.LuckLotteryException;

public class RSAAlgorithmException extends LuckLotteryException {

    public static final LuckLotteryException EXCEPTION = new RSAAlgorithmException();


    private RSAAlgorithmException() {
        super(ErrorCode.NO_SUCH_RSA_ALGORITHM);
    }
}
