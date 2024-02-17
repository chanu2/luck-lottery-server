package uttugseuja.lucklotteryserver.domain.notification.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uttugseuja.lucklotteryserver.domain.notification.domain.DeviceToken;
import uttugseuja.lucklotteryserver.domain.notification.domain.repository.DeviceTokenRepository;
import uttugseuja.lucklotteryserver.domain.notification.presentation.dto.request.RegisterDeviceTokenRequest;
import uttugseuja.lucklotteryserver.domain.user.domain.User;
import uttugseuja.lucklotteryserver.global.utils.user.UserUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class NotificationService {

    private final DeviceTokenRepository deviceTokenRepository;
    private final EntityManager entityManager;
    private final UserUtils userUtils;
    private final NotificationUtils notificationUtils;

    @Transactional
    public void registerDeviceToken(RegisterDeviceTokenRequest request) {
        User user = userUtils.getUserFromSecurityContext();

        Optional<DeviceToken> deviceTokenOptional =
                deviceTokenRepository.findByDeviceId(request.getDeviceId());

        deviceTokenOptional.ifPresentOrElse(
                deviceToken -> {
                    if (deviceToken.getUser().equals(user)) {
                        deviceToken.changeToken(request.getFcmToken());
                    } else {
                        deviceTokenRepository.deleteById(deviceToken.getId());
                        entityManager.flush();
                        deviceTokenRepository.save(
                                DeviceToken.of(
                                        user, request.getDeviceId(), request.getFcmToken()));
                    }
                },
                () ->
                        deviceTokenRepository.save(
                                DeviceToken.of(
                                        user, request.getDeviceId(), request.getFcmToken())));
    }

    public void test() {
        notificationUtils.sendLotteryNotification();
    }
}
