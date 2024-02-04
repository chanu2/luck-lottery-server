package uttugseuja.lucklotteryserver.domain.pensionlottery.presentation.dto.response;

import lombok.Getter;
import java.util.List;

@Getter
public class RandomPensionLotteryResponse {

    private Integer pensionRound;
    private Integer pensionGroup;
    private Integer pensionFirstNum;
    private Integer pensionSecondNum;
    private Integer pensionThirdNum;
    private Integer pensionFourthNum;
    private Integer pensionFifthNum;
    private Integer pensionSixthNum;

    public RandomPensionLotteryResponse(List<Integer> randomNumbers, int pensionRound) {
        this.pensionRound = pensionRound;
        this.pensionGroup = randomNumbers.get(0);
        this.pensionFirstNum = randomNumbers.get(1);
        this.pensionSecondNum = randomNumbers.get(2);
        this.pensionThirdNum = randomNumbers.get(3);
        this.pensionFourthNum = randomNumbers.get(4);
        this.pensionFifthNum = randomNumbers.get(5);
        this.pensionSixthNum = randomNumbers.get(6);
    }
}
