package uttugseuja.lucklotteryserver.domain.notification.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uttugseuja.lucklotteryserver.domain.notification.domain.DeviceToken;
import uttugseuja.lucklotteryserver.domain.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Long> {

    Optional<DeviceToken> findByDeviceId(String deviceId);
    void deleteById(Long deviceTokenId);

    @Query("select d from DeviceToken d where d.user.lotteryNotificationStatus = true")
    List<DeviceToken> findByUserOnLotteryNotification();

    @Query("select d from DeviceToken d where d.user.pensionLotteryNotificationStatus = true")
    List<DeviceToken> findByUserOnPensionLotteryNotification();

    void deleteByUser(User user);
}
