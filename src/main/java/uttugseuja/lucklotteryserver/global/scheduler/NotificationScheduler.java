package uttugseuja.lucklotteryserver.global.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uttugseuja.lucklotteryserver.domain.notification.service.NotificationUtils;

@RequiredArgsConstructor
@Service
public class NotificationScheduler {

    private final NotificationUtils notificationUtils;

    @Scheduled(cron = "0 0 8 * * 6")
    public void sendLotteryNotificationJob() {
        notificationUtils.sendLotteryNotification();
    }

    @Scheduled(cron = "0 0 8 * * 4")
    public void sendPensionLotteryNotificationJob() {
        notificationUtils.sendPensionLotteryNotification();
    }
}
