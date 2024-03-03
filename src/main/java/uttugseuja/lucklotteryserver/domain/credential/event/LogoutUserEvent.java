package uttugseuja.lucklotteryserver.domain.credential.event;

import lombok.Builder;
import lombok.Getter;
import uttugseuja.lucklotteryserver.global.event.DomainEvent;

@Getter
@Builder
public class LogoutUserEvent implements DomainEvent {
    private final Long userId;
}
