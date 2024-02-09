package uttugseuja.lucklotteryserver.domain.pensionlottery.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class CreatePensionLotteryRequest {

    private Integer pensionGroup;
    private Integer pensionFirstNum;
    private Integer pensionSecondNum;
    private Integer pensionThirdNum;
    private Integer pensionFourthNum;
    private Integer pensionFifthNum;
    private Integer pensionSixthNum;

}
