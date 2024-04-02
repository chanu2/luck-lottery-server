package uttugseuja.lucklotteryserver.domain.user.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import uttugseuja.lucklotteryserver.domain.lottery.domain.Lottery;
import uttugseuja.lucklotteryserver.domain.notification.event.DeviceTokenEvent;
import uttugseuja.lucklotteryserver.domain.pensionlottery.domain.PensionLottery;
import uttugseuja.lucklotteryserver.domain.user.domain.vo.UserInfoVO;
import uttugseuja.lucklotteryserver.global.event.Events;
import java.util.ArrayList;
import java.util.List;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<PensionLottery> pensionLotteries = new ArrayList<>();

    private String nickname;

    private String oauthProvider;

    private String oauthId;

    private String email;

    @Nullable
    private String profilePath;

    @Enumerated(EnumType.STRING)
    private AccountRole accountRole = AccountRole.USER;

    private Boolean lotteryNotificationStatus;

    private Boolean pensionLotteryNotificationStatus;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Lottery> lotteries = new ArrayList<>();

    @Builder
    public User(
            Long id,
            String nickname,
            String oauthProvider,
            String oauthId,
            String email,
            String profilePath) {
        this.id = id;
        this.nickname = nickname;
        this.oauthProvider = oauthProvider;
        this.oauthId = oauthId;
        this.email = email;
        this.profilePath = profilePath;
        this.lotteryNotificationStatus = true;
        this.pensionLotteryNotificationStatus = true;
    }

    public UserInfoVO getUserInfo() {
        return UserInfoVO.builder()
                .userId(id)
                .oauthProvider(oauthProvider)
                .nickname(nickname)
                .email(email)
                .profilePath(profilePath)
                .lotteryNotificationStatus(lotteryNotificationStatus)
                .pensionLotteryNotificationStatus(pensionLotteryNotificationStatus)
                .build();
    }

    public void updateProfilePath(String profilePath){
        this.profilePath = profilePath;
    }

    public void updateLotteryNotificationStatus(Boolean notificationStatus) {
        this.lotteryNotificationStatus = notificationStatus;
    }

    public void updatePensionLotteryNotificationStatus(Boolean notificationStatus) {
        this.pensionLotteryNotificationStatus = notificationStatus;
    }

    public void updateNickname(String nickname){
        this.nickname = nickname;
    }

    public static User of(Long userId) {
        return User.builder().id(userId).build();
    }

    public void handleDeleteDeviceToken() {
        DeviceTokenEvent deviceTokenEvent = new DeviceTokenEvent(User.of(id));
        Events.raise(deviceTokenEvent);
    }

}
