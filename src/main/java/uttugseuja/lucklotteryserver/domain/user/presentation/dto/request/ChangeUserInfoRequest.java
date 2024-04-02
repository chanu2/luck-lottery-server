package uttugseuja.lucklotteryserver.domain.user.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@NoArgsConstructor
public class ChangeUserInfoRequest {

    @Nullable
    private String profilePath;

    @NotNull
    private String nickname;
}
