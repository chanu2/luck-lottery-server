package uttugseuja.lucklotteryserver.domain.WinningPensionlottery.exception;


import uttugseuja.lucklotteryserver.global.error.exception.ErrorCode;
import uttugseuja.lucklotteryserver.global.error.exception.LuckLotteryIoException;

public class CrawlingIOException extends LuckLotteryIoException {
    public static final LuckLotteryIoException EXCEPTION = new CrawlingIOException();
    private CrawlingIOException() {
        super(ErrorCode.CRAWLING_IO_EXCEPTION);
    }
}
