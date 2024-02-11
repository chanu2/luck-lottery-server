package uttugseuja.lucklotteryserver.domain.asset.presentation.dto.response;

import lombok.Getter;
import uttugseuja.lucklotteryserver.domain.asset.domain.ProfileImage;

@Getter
public class ProfileImageDto {

    private Long id;
    private String url;
    private String title;

    public ProfileImageDto(ProfileImage profileImage) {
        this.id = profileImage.getId();
        this.url = profileImage.getImageUrl();
        this.title = profileImage.getTitle();
    }
}