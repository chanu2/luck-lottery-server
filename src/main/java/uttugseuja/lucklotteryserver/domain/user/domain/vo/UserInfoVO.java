package uttugseuja.lucklotteryserver.domain.user.domain.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoVO {
    private final Long userId;
    private final String nickname;
    private final String email;
    private final String profilePath;

}