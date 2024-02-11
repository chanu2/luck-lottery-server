package uttugseuja.lucklotteryserver.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uttugseuja.lucklotteryserver.domain.asset.service.AssetUtils;
import uttugseuja.lucklotteryserver.domain.image.service.ImageUtils;
import uttugseuja.lucklotteryserver.domain.user.domain.User;
import uttugseuja.lucklotteryserver.domain.user.presentation.dto.request.ChangeProfileRequest;
import uttugseuja.lucklotteryserver.domain.user.presentation.dto.response.UserProfileResponse;
import uttugseuja.lucklotteryserver.global.utils.user.UserUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserUtils userUtils;
    private final AssetUtils assetUtils;
    private final ImageUtils imageUtils;

    @Transactional
    public UserProfileResponse changeProfilePath(ChangeProfileRequest changeProfileRequest){
        User user = userUtils.getUserFromSecurityContext();
        log.info(user.getProfilePath());
        deleteUserProfilePath(user.getProfilePath());
        String imageUrl = changeProfileRequest.getProfilePath();
        user.updateProfilePath(imageUrl);
        return new UserProfileResponse(user.getUserInfo());
    }


    private void deleteUserProfilePath(String profilePath){
        if (!assetUtils.checkIsBasicProfile(profilePath)) {

            imageUtils.delete(profilePath);
        }
    }

}
