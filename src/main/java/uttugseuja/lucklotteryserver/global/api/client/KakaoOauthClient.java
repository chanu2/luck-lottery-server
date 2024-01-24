package uttugseuja.lucklotteryserver.global.api.client;


import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import uttugseuja.lucklotteryserver.global.api.dto.OIDCKeysResponse;

@FeignClient(name = "KakaoAuthClient", url = "https://kauth.kakao.com")
public interface KakaoOauthClient {

    @Cacheable(cacheNames = "KakaoOICD", cacheManager = "oidcKeyCacheManager")
    @GetMapping("/.well-known/jwks.json")
    OIDCKeysResponse getKakaoOIDCOpenKeys();
}
