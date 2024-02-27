package uttugseuja.lucklotteryserver.domain.WinningPensionlottery.exception;


import uttugseuja.lucklotteryserver.global.error.exception.ErrorCode;
import uttugseuja.lucklotteryserver.global.error.exception.LuckLotteryException;

public class CrawlingIOException extends LuckLotteryException {
    public static final LuckLotteryException EXCEPTION = new CrawlingIOException();
    private CrawlingIOException() {
        super(ErrorCode.CRAWLING_IO_EXCEPTION);
    }
}
