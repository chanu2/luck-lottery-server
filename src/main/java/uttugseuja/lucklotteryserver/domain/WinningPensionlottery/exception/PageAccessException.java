package uttugseuja.lucklotteryserver.domain.WinningPensionlottery.exception;


import uttugseuja.lucklotteryserver.global.error.exception.ErrorCode;
import uttugseuja.lucklotteryserver.global.error.exception.LuckLotteryException;

public class PageAccessException extends LuckLotteryException {
    public static final LuckLotteryException EXCEPTION = new PageAccessException();
    private PageAccessException() {
        super(ErrorCode.PAGE_ACCESS_LOTTERY_ERROR);
    }
}
