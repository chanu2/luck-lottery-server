package uttugseuja.lucklotteryserver.domain.winning_lottery.exception;

import uttugseuja.lucklotteryserver.global.error.exception.ErrorCode;
import uttugseuja.lucklotteryserver.global.error.exception.LuckLotteryException;

public class WinningLotteryNotFoundException extends LuckLotteryException {

    public static final LuckLotteryException EXCEPTION = new WinningLotteryNotFoundException();

    private WinningLotteryNotFoundException() {
        super(ErrorCode.WINNING_LOTTERY_NOT_FOUND);
    }
}
