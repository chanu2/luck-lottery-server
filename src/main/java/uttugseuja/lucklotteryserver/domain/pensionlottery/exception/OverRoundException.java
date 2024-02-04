package uttugseuja.lucklotteryserver.domain.pensionlottery.exception;

import uttugseuja.lucklotteryserver.global.error.exception.ErrorCode;
import uttugseuja.lucklotteryserver.global.error.exception.LuckLotteryException;

public class OverRoundException extends LuckLotteryException {

    public static final LuckLotteryException EXCEPTION = new OverRoundException();

    public OverRoundException() {
        super(ErrorCode.OVER_LOTTERY_ROUND);
    }
}
