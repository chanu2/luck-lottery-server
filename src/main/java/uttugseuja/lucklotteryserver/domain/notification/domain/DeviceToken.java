package uttugseuja.lucklotteryserver.domain.notification.domain;

import jakarta.persistence.*;
import lombok.*;
import uttugseuja.lucklotteryserver.domain.user.domain.User;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class DeviceToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_token_id")
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(unique = true)
    private String deviceId;

    private String fcmToken;

    public static DeviceToken of(User user, String deviceId, String fcmToken) {
        return DeviceToken.builder()
                .user(user)
                .deviceId(deviceId)
                .fcmToken(fcmToken)
                .build();
    }

    public void changeToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public Long getUserId() {
        return this.user.getId();
    }
}
