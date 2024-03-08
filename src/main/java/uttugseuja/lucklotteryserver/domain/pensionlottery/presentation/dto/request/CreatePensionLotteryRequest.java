package uttugseuja.lucklotteryserver.domain.pensionlottery.presentation.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePensionLotteryRequest {

    @Min(1) @Max(5)
    private Integer pensionGroup;

    @Min(0) @Max(9)
    private Integer pensionFirstNum;

    @Min(0) @Max(9)
    private Integer pensionSecondNum;

    @Min(0) @Max(9)
    private Integer pensionThirdNum;

    @Min(0) @Max(9)
    private Integer pensionFourthNum;

    @Min(0) @Max(9)
    private Integer pensionFifthNum;

    @Min(0) @Max(9)
    private Integer pensionSixthNum;

}
