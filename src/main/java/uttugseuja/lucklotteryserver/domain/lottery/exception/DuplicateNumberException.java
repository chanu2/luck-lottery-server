package uttugseuja.lucklotteryserver.domain.lottery.exception;

import uttugseuja.lucklotteryserver.global.error.exception.ErrorCode;
import uttugseuja.lucklotteryserver.global.error.exception.LuckLotteryException;

public class DuplicateNumberException extends LuckLotteryException {

    public static final LuckLotteryException EXCEPTION = new DuplicateNumberException();

    public DuplicateNumberException(){
        super(ErrorCode.DUPLICATE_NUMBER);
    }
}
