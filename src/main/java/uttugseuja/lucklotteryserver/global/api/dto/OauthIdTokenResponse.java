package uttugseuja.lucklotteryserver.global.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OauthIdTokenResponse {
    @JsonProperty("id_token")
    private String idToken;
}
