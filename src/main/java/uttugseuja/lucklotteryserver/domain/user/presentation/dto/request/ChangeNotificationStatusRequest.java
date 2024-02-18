package uttugseuja.lucklotteryserver.domain.user.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChangeNotificationStatusRequest {

    @NotNull
    private Boolean notificationStatus;
}
