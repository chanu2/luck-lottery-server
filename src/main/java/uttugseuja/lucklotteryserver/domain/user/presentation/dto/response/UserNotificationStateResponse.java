package uttugseuja.lucklotteryserver.domain.user.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uttugseuja.lucklotteryserver.domain.user.domain.vo.UserInfoVO;

@Getter
@AllArgsConstructor
public class UserNotificationStateResponse {

    private final Boolean lotteryNotificationStatus;

    private final Boolean pensionLotteryNotificationStatus;

    public UserNotificationStateResponse(UserInfoVO userInfo) {
        this.lotteryNotificationStatus = userInfo.getLotteryNotificationStatus();
        this.pensionLotteryNotificationStatus = userInfo.getPensionLotteryNotificationStatus();
    }
}
