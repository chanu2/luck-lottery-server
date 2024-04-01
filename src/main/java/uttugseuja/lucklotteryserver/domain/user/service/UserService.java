package uttugseuja.lucklotteryserver.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uttugseuja.lucklotteryserver.domain.image.service.ImageUtils;
import uttugseuja.lucklotteryserver.domain.user.domain.User;
import uttugseuja.lucklotteryserver.domain.user.presentation.dto.request.ChangeNicknameRequest;
import uttugseuja.lucklotteryserver.domain.user.presentation.dto.request.ChangeNotificationStatusRequest;
import uttugseuja.lucklotteryserver.domain.user.presentation.dto.request.ChangeProfileRequest;
import uttugseuja.lucklotteryserver.domain.user.presentation.dto.request.ChangeUserInfoRequest;
import uttugseuja.lucklotteryserver.domain.user.presentation.dto.response.UserInfoResponse;
import uttugseuja.lucklotteryserver.domain.user.presentation.dto.response.UserProfileResponse;
import uttugseuja.lucklotteryserver.global.utils.user.UserUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserUtils userUtils;
    private final ImageUtils imageUtils;

    @Transactional
    public void changeNickname(ChangeNicknameRequest changeNicknameRequest){
        User user = userUtils.getUserFromSecurityContext();
        user.updateNickname(changeNicknameRequest.getNickname());
    }

    @Transactional
    public UserProfileResponse changeProfilePath(ChangeProfileRequest changeProfileRequest){
        User user = userUtils.getUserFromSecurityContext();
        String profilePath = user.getProfilePath();

        if(profilePath != null){
            deleteUserProfilePath(user.getProfilePath());
        }

        String imageUrl = changeProfileRequest.getProfilePath();
        user.updateProfilePath(imageUrl);
        return new UserProfileResponse(user.getUserInfo());
    }

    @Transactional
    public void changeLotteryNotificationStatus(ChangeNotificationStatusRequest changeNotificationStatusRequest) {
        User user = userUtils.getUserFromSecurityContext();

        user.updateLotteryNotificationStatus(changeNotificationStatusRequest.getNotificationStatus());
    }

    @Transactional
    public void changePensionLotteryNotificationStatus(ChangeNotificationStatusRequest changeNotificationStatusRequest) {
        User user = userUtils.getUserFromSecurityContext();

        user.updatePensionLotteryNotificationStatus(changeNotificationStatusRequest.getNotificationStatus());
    }

    private void deleteUserProfilePath(String profilePath){

        imageUtils.delete(profilePath);

    }

    public UserInfoResponse getUserInfo() {
        User user = userUtils.getUserFromSecurityContext();
        return new UserInfoResponse(user.getUserInfo());
    }

    @Transactional
    public UserInfoResponse changeUserInfo(ChangeUserInfoRequest changeUserInfoRequest) {
        User user = userUtils.getUserFromSecurityContext();
        String nowProfilePath = user.getProfilePath();
        String updateProfile = changeUserInfoRequest.getProfilePath();

        if(updateProfile == null && nowProfilePath != null) {

            deleteUserProfilePath(user.getProfilePath());
            user.updateProfilePath(null);

        }else if(updateProfile != null && nowProfilePath == null) {
            user.updateProfilePath(updateProfile);

        }else if(updateProfile != null && nowProfilePath != null){

            if(!updateProfile.equals(nowProfilePath)){
                deleteUserProfilePath(user.getProfilePath());
                user.updateProfilePath(updateProfile);
            }
        }

        if(!(changeUserInfoRequest.getNickname() == null)){
            user.updateNickname(changeUserInfoRequest.getNickname());
        }

        return new UserInfoResponse(user.getUserInfo());

    }


}
