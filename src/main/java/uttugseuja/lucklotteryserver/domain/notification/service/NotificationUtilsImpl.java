package uttugseuja.lucklotteryserver.domain.notification.service;

import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.MessagingErrorCode;
import com.google.firebase.messaging.SendResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uttugseuja.lucklotteryserver.domain.notification.domain.NotificationMessage;
import uttugseuja.lucklotteryserver.domain.notification.domain.DeviceToken;
import uttugseuja.lucklotteryserver.domain.notification.domain.repository.DeviceTokenRepository;
import uttugseuja.lucklotteryserver.domain.notification.exception.FcmTokenInvalidException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationUtilsImpl implements NotificationUtils{

    private final FCMService fcmService;
    private final DeviceTokenRepository deviceTokenRepository;

    @Override
    public void sendLotteryNotification() {
        List<DeviceToken> deviceTokens = deviceTokenRepository.findByUserOnLotteryNotification();
        List<String> fcmTokens = getFcmTokens(deviceTokens);

        NotificationMessage lotteryMessage = NotificationMessage.LOTTERY;

        if (fcmTokens.isEmpty()) {
            return;
        }

        ApiFuture<BatchResponse> batchResponseApiFuture =
                fcmService.sendGroupMessageAsync(
                        fcmTokens, lotteryMessage.getTitle(), lotteryMessage.getContent());

        checkFcmResponse(deviceTokens, fcmTokens, batchResponseApiFuture);
    }

    @Override
    public void sendPensionLotteryNotification() {
        List<DeviceToken> deviceTokens = deviceTokenRepository.findByUserOnPensionLotteryNotification();
        List<String> fcmTokens = getFcmTokens(deviceTokens);

        NotificationMessage pensionLotteryMessage = NotificationMessage.PENSION_LOTTERY;

        if (fcmTokens.isEmpty()) {
            return;
        }

        ApiFuture<BatchResponse> batchResponseApiFuture =
                fcmService.sendGroupMessageAsync(
                        fcmTokens, pensionLotteryMessage.getTitle(), pensionLotteryMessage.getContent());

        checkFcmResponse(deviceTokens, fcmTokens, batchResponseApiFuture);
    }

    private List<String> getFcmTokens(List<DeviceToken> deviceTokens) {
        return deviceTokens.stream().map(DeviceToken::getFcmToken).collect(Collectors.toList());
    }

    private void checkFcmResponse(
            List<DeviceToken> deviceTokens,
            List<String> tokens,
            ApiFuture<BatchResponse> batchResponseApiFuture) {
        try {
            List<SendResponse> responses = batchResponseApiFuture.get().getResponses();
            IntStream.iterate(0, i -> i + 1)
                    .limit(responses.size())
                    .filter(i -> responses.get(i).getException() != null)
                    .filter(
                            i ->
                                    responses
                                            .get(i)
                                            .getException()
                                            .getMessagingErrorCode()
                                            .equals(MessagingErrorCode.INVALID_ARGUMENT))
                    .forEach(
                            i -> {
                                String errorToken = tokens.get(i);
                                String errorMessage = responses.get(i).getException().getMessage();
                                MessagingErrorCode errorCode =
                                        responses.get(i).getException().getMessagingErrorCode();

                                Optional<DeviceToken> errorDeviceToken =
                                        deviceTokens.stream()
                                                .filter(
                                                        deviceToken ->
                                                                deviceToken
                                                                        .getFcmToken()
                                                                        .equals(errorToken))
                                                .findAny();
                                Long IdOfErrorDeviceToken =
                                        errorDeviceToken.map(DeviceToken::getId).orElse(null);
                                Long userIdOfErrorDeviceToken =
                                        errorDeviceToken.map(DeviceToken::getUserId).orElse(null);
                                deviceTokenRepository.deleteById(IdOfErrorDeviceToken);

                                log.error(
                                        "**[sendGroupMessageAsync] errorUserId: {}, errorMessage: {}, errorCode: {}, errorToken: {}",
                                        userIdOfErrorDeviceToken,
                                        errorMessage,
                                        errorCode,
                                        errorToken);
                            });
        } catch (InterruptedException | ExecutionException e) {
            throw FcmTokenInvalidException.EXCEPTION;
        }
    }
}
