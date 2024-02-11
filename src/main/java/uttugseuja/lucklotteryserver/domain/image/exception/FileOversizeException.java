package uttugseuja.lucklotteryserver.domain.image.exception;

import uttugseuja.lucklotteryserver.global.error.exception.ErrorCode;
import uttugseuja.lucklotteryserver.global.error.exception.LuckLotteryException;

public class FileOversizeException extends LuckLotteryException {

    public static final LuckLotteryException EXCEPTION = new FileOversizeException();

    private FileOversizeException() {
        super(ErrorCode.FILE_OVER_SIZE);
    }
}
