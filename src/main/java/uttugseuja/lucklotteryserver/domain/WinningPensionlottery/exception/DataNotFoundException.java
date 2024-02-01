package uttugseuja.lucklotteryserver.domain.WinningPensionlottery.exception;


import uttugseuja.lucklotteryserver.global.error.exception.ErrorCode;
import uttugseuja.lucklotteryserver.global.error.exception.LuckLotteryException;

public class DataNotFoundException extends LuckLotteryException {
    public static final LuckLotteryException EXCEPTION = new DataNotFoundException();
    private DataNotFoundException() {
        super(ErrorCode.DATA_NOT_FOUND);
    }
}
