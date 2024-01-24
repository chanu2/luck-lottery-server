package uttugseuja.lucklotteryserver.global.api.client;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import uttugseuja.lucklotteryserver.global.api.dto.OIDCKeysResponse;

@FeignClient(name = "GoogleAuthClient", url = "https://www.googleapis.com/oauth2")
public interface GoogleAuthClient {

    @Cacheable(cacheNames = "GoogleOICD", cacheManager = "oidcKeyCacheManager")
    @GetMapping("/v3/certs")
    OIDCKeysResponse getGoogleOIDCOpenKeys();
}

