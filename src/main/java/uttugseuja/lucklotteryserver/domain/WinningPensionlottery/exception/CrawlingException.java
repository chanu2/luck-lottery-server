package uttugseuja.lucklotteryserver.domain.WinningPensionlottery.exception;


import uttugseuja.lucklotteryserver.global.error.exception.ErrorCode;
import uttugseuja.lucklotteryserver.global.error.exception.LuckLotteryException;

public class CrawlingException extends LuckLotteryException {
    public static final LuckLotteryException EXCEPTION = new CrawlingException();
    private CrawlingException() {
        super(ErrorCode.CRAWLING_EXCEPTION);
    }
}
