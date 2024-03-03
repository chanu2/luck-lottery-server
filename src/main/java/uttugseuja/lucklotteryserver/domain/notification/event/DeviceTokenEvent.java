package uttugseuja.lucklotteryserver.domain.notification.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uttugseuja.lucklotteryserver.domain.user.domain.User;
import uttugseuja.lucklotteryserver.global.event.DomainEvent;

@Getter
@RequiredArgsConstructor
public class DeviceTokenEvent implements DomainEvent {

    private final User user;
}
