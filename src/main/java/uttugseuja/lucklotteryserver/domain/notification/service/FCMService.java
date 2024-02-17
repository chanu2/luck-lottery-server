package uttugseuja.lucklotteryserver.domain.notification.service;

import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FCMService {

    public ApiFuture<BatchResponse> sendGroupMessageAsync(
            List<String> fcmTokens, String title, String content) {
        MulticastMessage multicastMessage =
                MulticastMessage.builder()
                        .addAllTokens(fcmTokens)
                        .putData("title", title)
                        .putData("content", content)
                        .setApnsConfig(
                                ApnsConfig.builder()
                                        .setAps(Aps.builder().setSound("default").build())
                                        .build())
                        .build();

        return FirebaseMessaging.getInstance().sendMulticastAsync(multicastMessage);
    }
}
