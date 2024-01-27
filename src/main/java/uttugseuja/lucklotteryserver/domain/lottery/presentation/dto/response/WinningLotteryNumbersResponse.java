package uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.response;

import lombok.Getter;
import uttugseuja.lucklotteryserver.global.api.dto.WinningLotteryDto;

@Getter
public class WinningLotteryNumbersResponse {

    private Integer firstNum;

    private Integer secondNum;

    private Integer thirdNum;

    private Integer fourthNum;

    private Integer fifthNum;

    private Integer sixthNum;

    private Integer bonusNum;

    public WinningLotteryNumbersResponse(WinningLotteryDto winningLotteryDto) {
        this.firstNum = winningLotteryDto.getDrwtNo1();
        this.secondNum = winningLotteryDto.getDrwtNo2();
        this.thirdNum = winningLotteryDto.getDrwtNo3();
        this.fourthNum = winningLotteryDto.getDrwtNo4();
        this.fifthNum = winningLotteryDto.getDrwtNo5();
        this.sixthNum = winningLotteryDto.getDrwtNo6();
        this.bonusNum = winningLotteryDto.getBnusNo();
    }
}
