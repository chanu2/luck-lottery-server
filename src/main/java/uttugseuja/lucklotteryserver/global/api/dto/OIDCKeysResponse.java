package uttugseuja.lucklotteryserver.global.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OIDCKeysResponse {
    List<OIDCKeyDto> keys;
}
