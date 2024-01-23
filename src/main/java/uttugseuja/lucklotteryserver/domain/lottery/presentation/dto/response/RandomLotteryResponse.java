package uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.response;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class RandomLotteryResponse {

    private Integer round;

    private LocalDate winningDate;

    private Integer firstNum;

    private Integer secondNum;

    private Integer thirdNum;

    private Integer fourthNum;

    private Integer fifthNum;

    private Integer sixthNum;

    public RandomLotteryResponse(List<Integer> randomNumbers, int round, LocalDate winningDate) {
        this.round = round;
        this.winningDate = winningDate;
        this.firstNum = randomNumbers.get(0);
        this.secondNum = randomNumbers.get(1);
        this.thirdNum = randomNumbers.get(2);
        this.fourthNum = randomNumbers.get(3);
        this.fifthNum = randomNumbers.get(4);
        this.sixthNum = randomNumbers.get(5);
    }
}
