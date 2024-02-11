package uttugseuja.lucklotteryserver.domain.asset.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uttugseuja.lucklotteryserver.domain.asset.presentation.dto.response.ProfileImageDto;
import uttugseuja.lucklotteryserver.domain.asset.service.AssetService;

@RequiredArgsConstructor
@RequestMapping("/api/v1/asset")
@RestController
@Tag(name = "이미지 관련 컨트롤러", description = "")
@SecurityRequirement(name = "access-token")
public class AssetController {

    private final AssetService assetService;

    @Operation(summary = "프로필 이미지 랜덤하게 받기")
    @GetMapping("/profiles/random")
    public ProfileImageDto getRandomProfileImageUrl() {
        return assetService.getRandomProfileImage();
    }

}
