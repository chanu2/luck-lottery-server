package uttugseuja.lucklotteryserver.domain.asset.service;

import uttugseuja.lucklotteryserver.domain.asset.presentation.dto.response.ProfileImageDto;

public interface AssetUtils {

    ProfileImageDto getRandomProfileImage();

    Boolean checkIsBasicProfile(String profileUrl);
}
