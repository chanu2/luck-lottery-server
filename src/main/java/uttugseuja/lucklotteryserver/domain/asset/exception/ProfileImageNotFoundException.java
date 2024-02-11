package uttugseuja.lucklotteryserver.domain.asset.exception;

import uttugseuja.lucklotteryserver.global.error.exception.ErrorCode;
import uttugseuja.lucklotteryserver.global.error.exception.LuckLotteryException;

public class ProfileImageNotFoundException extends LuckLotteryException {

    public static final LuckLotteryException EXCEPTION = new ProfileImageNotFoundException();

    private ProfileImageNotFoundException() {
        super(ErrorCode.PROFILE_IMAGE_NOT_FOUND);
    }
}