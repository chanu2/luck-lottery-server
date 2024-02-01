package uttugseuja.lucklotteryserver.domain.WinningPensionlottery.exception;


import uttugseuja.lucklotteryserver.global.error.exception.ErrorCode;
import uttugseuja.lucklotteryserver.global.error.exception.LuckLotteryException;

public class WinningPensionLotteryNotFoundException extends LuckLotteryException {
    public static final LuckLotteryException EXCEPTION = new WinningPensionLotteryNotFoundException();
    private WinningPensionLotteryNotFoundException() {
        super(ErrorCode.WINNING_PENSION_LOTTERY_NOT_FOUND);
    }
}
