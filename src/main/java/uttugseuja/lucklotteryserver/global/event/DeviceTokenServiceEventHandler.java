package uttugseuja.lucklotteryserver.global.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import uttugseuja.lucklotteryserver.domain.notification.domain.repository.DeviceTokenRepository;
import uttugseuja.lucklotteryserver.domain.notification.event.DeviceTokenEvent;

@RequiredArgsConstructor
@Component
@Slf4j
public class DeviceTokenServiceEventHandler {

    private final DeviceTokenRepository deviceTokenRepository;

    @TransactionalEventListener(
            classes = DeviceTokenEvent.class,
            phase = TransactionPhase.BEFORE_COMMIT)
    public void deleteFcmToken(DeviceTokenEvent event) {
        deviceTokenRepository.deleteByUser(event.getUser());
    }
}
