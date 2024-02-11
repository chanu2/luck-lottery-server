package uttugseuja.lucklotteryserver.domain.user.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uttugseuja.lucklotteryserver.domain.user.domain.vo.UserInfoVO;

@Getter
@AllArgsConstructor
public class UserProfileResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final String profilePath;


    public UserProfileResponse(UserInfoVO userInfo) {
        this.id = userInfo.getUserId();
        this.name = userInfo.getNickname();
        this.email = userInfo.getEmail();
        this.profilePath = userInfo.getProfilePath();
    }
}
