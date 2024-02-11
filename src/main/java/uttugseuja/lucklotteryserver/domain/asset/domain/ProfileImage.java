package uttugseuja.lucklotteryserver.domain.asset.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class ProfileImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String imageUrl;

}