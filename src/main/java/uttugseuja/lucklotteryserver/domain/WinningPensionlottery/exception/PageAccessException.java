package uttugseuja.lucklotteryserver.domain.WinningPensionlottery.exception;


import uttugseuja.lucklotteryserver.global.error.exception.ErrorCode;
import uttugseuja.lucklotteryserver.global.error.exception.LuckLotteryIoException;

public class PageAccessException extends LuckLotteryIoException {
    public static final LuckLotteryIoException EXCEPTION = new PageAccessException();
    private PageAccessException() {
        super(ErrorCode.PAGE_ACCESS_LOTTERY_ERROR);
    }
}
