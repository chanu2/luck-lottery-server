package uttugseuja.lucklotteryserver.domain.user.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uttugseuja.lucklotteryserver.domain.user.presentation.dto.request.ChangeNotificationStatusRequest;
import uttugseuja.lucklotteryserver.domain.user.presentation.dto.request.ChangeProfileRequest;
import uttugseuja.lucklotteryserver.domain.user.presentation.dto.response.UserProfileResponse;
import uttugseuja.lucklotteryserver.domain.user.service.UserService;


@Tag(name = "유저", description = "유저 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "유저 프로필 이미지 변경하기")
    @PatchMapping("/profile")
    public UserProfileResponse changeProfilePath(@Valid @RequestBody ChangeProfileRequest changeProfileRequest){
        return userService.changeProfilePath(changeProfileRequest);
    }

    @Operation(summary = "유저 로또 알림 on/off 변경하기")
    @PatchMapping("/lottery/notification")
    public void changeLotteryNotificationStatus(
            @Valid @RequestBody ChangeNotificationStatusRequest changeNotificationStatusRequest) {
        userService.changeLotteryNotificationStatus(changeNotificationStatusRequest);
    }

    @Operation(summary = "유저 연금복권 알림 on/off 변경하기")
    @PatchMapping("/pension/lottery/notification")
    public void changePensionLotteryNotificationStatus(
            @Valid @RequestBody ChangeNotificationStatusRequest changeNotificationStatusRequest) {
        userService.changePensionLotteryNotificationStatus(changeNotificationStatusRequest);
    }
 }
