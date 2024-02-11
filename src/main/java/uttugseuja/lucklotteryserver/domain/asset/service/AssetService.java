package uttugseuja.lucklotteryserver.domain.asset.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uttugseuja.lucklotteryserver.domain.asset.domain.ProfileImage;
import uttugseuja.lucklotteryserver.domain.asset.domain.repository.ProfileImageRepository;
import uttugseuja.lucklotteryserver.domain.asset.exception.ProfileImageNotFoundException;
import uttugseuja.lucklotteryserver.domain.asset.presentation.dto.response.ProfileImageDto;

@Service
@RequiredArgsConstructor
public class AssetService implements AssetUtils{

    private final ProfileImageRepository profileImageRepository;

    @Override
    public ProfileImageDto getRandomProfileImage() {
        ProfileImage randomProfileImage =
                profileImageRepository
                        .findRandomProfileImage()
                        .orElseThrow(() -> ProfileImageNotFoundException.EXCEPTION);
        return new ProfileImageDto(randomProfileImage);
    }


    @Override
    public Boolean checkIsBasicProfile(String profileUrl){
        return profileImageRepository.existsByImageUrl(profileUrl);
    }
}