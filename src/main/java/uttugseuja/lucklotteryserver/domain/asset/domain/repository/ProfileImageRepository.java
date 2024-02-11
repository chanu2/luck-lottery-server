package uttugseuja.lucklotteryserver.domain.asset.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uttugseuja.lucklotteryserver.domain.asset.domain.ProfileImage;
import java.util.Optional;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {

    @Query(value = "SELECT * FROM tbl_profile_image order by RAND() limit 1", nativeQuery = true)
    Optional<ProfileImage> findRandomProfileImage();

    Boolean existsByImageUrl(String profileUrl);
}