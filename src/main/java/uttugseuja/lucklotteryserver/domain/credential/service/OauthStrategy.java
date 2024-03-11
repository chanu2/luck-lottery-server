package uttugseuja.lucklotteryserver.domain.credential.service;

import uttugseuja.lucklotteryserver.domain.credential.presentation.dto.request.UnlinkRequest;
import uttugseuja.lucklotteryserver.domain.credential.presentation.dto.response.OauthTokenInfoDto;
import uttugseuja.lucklotteryserver.global.api.dto.UserInfoToOauthDto;

public interface OauthStrategy {
    OIDCDecodePayload getOIDCDecodePayload(String token);

    String getOauthLink();

    OauthTokenInfoDto getOauthToken(String code);

    UserInfoToOauthDto getUserInfo(String oauthAccessToken);

    void unLink(UnlinkRequest unlinkRequest);
}
