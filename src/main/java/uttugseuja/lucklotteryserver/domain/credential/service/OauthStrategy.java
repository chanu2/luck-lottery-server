package uttugseuja.lucklotteryserver.domain.credential.service;

import uttugseuja.lucklotteryserver.domain.credential.presentation.dto.response.OauthTokenInfoDto;

public interface OauthStrategy {
    OIDCDecodePayload getOIDCDecodePayload(String token);

    String getOauthLink();

    OauthTokenInfoDto getOauthToken(String code);
}
