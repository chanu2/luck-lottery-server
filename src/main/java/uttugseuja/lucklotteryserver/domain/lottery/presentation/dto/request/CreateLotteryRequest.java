package uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateLotteryRequest {

    @NotNull(message = "null값을 허용하지 않습니다.")
    @Min(value = 1, message = "1 미만의 값을 허용하지 않습니다.")
    @Max(value = 45, message = "45 초과의 값을 허용하지 않습니다.")
    private Integer firstNum;

    @NotNull(message = "null값을 허용하지 않습니다.")
    @Min(value = 1, message = "1 미만의 값을 허용하지 않습니다.")
    @Max(value = 45, message = "45 초과의 값을 허용하지 않습니다.")
    private Integer secondNum;

    @NotNull(message = "null값을 허용하지 않습니다.")
    @Min(value = 1, message = "1 미만의 값을 허용하지 않습니다.")
    @Max(value = 45, message = "45 초과의 값을 허용하지 않습니다.")
    private Integer thirdNum;

    @NotNull(message = "null값을 허용하지 않습니다.")
    @Min(value = 1, message = "1 미만의 값을 허용하지 않습니다.")
    @Max(value = 45, message = "45 초과의 값을 허용하지 않습니다.")
    private Integer fourthNum;

    @NotNull(message = "null값을 허용하지 않습니다.")
    @Min(value = 1, message = "1 미만의 값을 허용하지 않습니다.")
    @Max(value = 45, message = "45 초과의 값을 허용하지 않습니다.")
    private Integer fifthNum;

    @NotNull(message = "null값을 허용하지 않습니다.")
    @Min(value = 1, message = "1 미만의 값을 허용하지 않습니다.")
    @Max(value = 45, message = "45 초과의 값을 허용하지 않습니다.")
    private Integer sixthNum;
}
