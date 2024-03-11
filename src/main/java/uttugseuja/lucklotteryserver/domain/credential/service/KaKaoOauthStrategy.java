package uttugseuja.lucklotteryserver.domain.credential.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uttugseuja.lucklotteryserver.domain.credential.presentation.dto.request.UnlinkRequest;
import uttugseuja.lucklotteryserver.domain.credential.presentation.dto.response.OauthTokenInfoDto;
import uttugseuja.lucklotteryserver.global.api.client.KakaoOauthClient;
import uttugseuja.lucklotteryserver.global.api.client.KakaoUnlinkClient;
import uttugseuja.lucklotteryserver.global.api.dto.OIDCKeysResponse;
import uttugseuja.lucklotteryserver.global.api.dto.OauthTokenResponse;
import uttugseuja.lucklotteryserver.global.api.dto.UserInfoToOauthDto;
import uttugseuja.lucklotteryserver.global.property.OauthProperties;

@AllArgsConstructor
@Component("KAKAO")
@Slf4j
public class KaKaoOauthStrategy implements OauthStrategy {

    private final OauthProperties oauthProperties;
    private final KakaoOauthClient kakaoOauthClient;
    private final OauthOIDCProvider oauthOIDCProvider;
    private final KakaoUnlinkClient kakaoUnlinkClient;
    private static final String PREFIX = "KakaoAK ";
    private static final String TARGET_TYPE = "user_id";
    private static final String ISSUER = "https://kauth.kakao.com";
    private static final String QUERY_STRING = "/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code";


    @Override
    public OIDCDecodePayload getOIDCDecodePayload(String token){
        OIDCKeysResponse oidcKakaoKeysResponse = kakaoOauthClient.getKakaoOIDCOpenKeys();
        return oauthOIDCProvider.getPayloadFromIdToken(token,ISSUER,oauthProperties.getKakaoClientId(),oidcKakaoKeysResponse);
    }

    @Override
    public String getOauthLink() {
        return oauthProperties.getKakaoBaseUrl()
                + String.format(
                QUERY_STRING,
                oauthProperties.getKakaoClientId(),
                oauthProperties.getKakaoRedirectUrl());
    }

    @Override
    public OauthTokenInfoDto getOauthToken(String code) {
        OauthTokenResponse oauthTokenResponse = kakaoOauthClient
                .kakaoAuth(
                        oauthProperties.getKakaoClientId(),
                        oauthProperties.getKakaoRedirectUrl(),
                        code);
        return OauthTokenInfoDto.builder()
                .idToken(oauthTokenResponse.getIdToken())
                .accessToken(oauthTokenResponse.getAccessToken())
                .build();
    }

    @Override
    public UserInfoToOauthDto getUserInfo(String oauthAccessToken) {
        return null;
    }

    @Override
    public void unLink(UnlinkRequest unlinkRequest) {

        if (unlinkRequest.getOauthId() != null) {
            String kakaoAdminKey = oauthProperties.getKakaoAdminKey();
            kakaoUnlinkClient.unlinkUser(PREFIX + kakaoAdminKey,TARGET_TYPE, Long.valueOf(unlinkRequest.getOauthId()));
        }

    }


}
