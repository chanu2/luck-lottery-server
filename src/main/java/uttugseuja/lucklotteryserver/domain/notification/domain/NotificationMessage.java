package uttugseuja.lucklotteryserver.domain.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationMessage {

    LOTTERY("로또 추첨 알림", "금일 오후 8시 35분경에 로또 추첨이 시작됩니다."),
    PENSION_LOTTERY("연금복권 추첨 알림", "금일 오후 7시 5분경에 연금복권 추첨이 시작됩니다.");

    String title;
    String content;
}
