package uttugseuja.lucklotteryserver.domain.lottery.exception;

import uttugseuja.lucklotteryserver.global.error.exception.ErrorCode;
import uttugseuja.lucklotteryserver.global.error.exception.LuckLotteryException;

public class BadRoundException extends LuckLotteryException {

    public static final LuckLotteryException EXCEPTION = new BadRoundException();

    public BadRoundException() {
        super(ErrorCode.LOTTERY_ROUND_NOT_FOUND);
    }
}
