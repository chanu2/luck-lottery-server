package uttugseuja.lucklotteryserver.domain.notification.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uttugseuja.lucklotteryserver.domain.notification.presentation.dto.request.RegisterDeviceTokenRequest;
import uttugseuja.lucklotteryserver.domain.notification.service.NotificationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
@Tag(name = "알림", description = "알림 관련 API")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "FCM토큰 저장")
    @PostMapping("/register/token")
    public void registerFcmToken(@Valid @RequestBody RegisterDeviceTokenRequest request) {
        notificationService.registerDeviceToken(request);
    }

    @GetMapping("/test")
    public void test() {
        notificationService.test();
    }
}
