package uttugseuja.lucklotteryserver.domain.notification.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RegisterDeviceTokenRequest {

    @NotBlank
    private String deviceId;

    @NotBlank
    private String fcmToken;
}
