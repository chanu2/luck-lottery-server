package uttugseuja.lucklotteryserver.domain.winning_lottery.presentation.dto.response;

import lombok.Getter;
import uttugseuja.lucklotteryserver.domain.winning_lottery.domain.vo.WinningLotteryBaseInfoVo;

import java.time.LocalDate;

@Getter
public class WinningLotteryResponse {

    private Integer round;

    private LocalDate winningDate;

    private Integer firstNum;

    private Integer secondNum;

    private Integer thirdNum;

    private Integer fourthNum;

    private Integer fifthNum;

    private Integer sixthNum;

    private Integer bonusNum;

    public WinningLotteryResponse(WinningLotteryBaseInfoVo winningLotteryBaseInfoVo) {
        this.round = winningLotteryBaseInfoVo.getRound();
        this.winningDate = winningLotteryBaseInfoVo.getWinningDate();
        this.firstNum = winningLotteryBaseInfoVo.getFirstNum();
        this.secondNum = winningLotteryBaseInfoVo.getSecondNum();
        this.thirdNum = winningLotteryBaseInfoVo.getThirdNum();
        this.fourthNum = winningLotteryBaseInfoVo.getFourthNum();
        this.fifthNum = winningLotteryBaseInfoVo.getFifthNum();
        this.sixthNum = winningLotteryBaseInfoVo.getSixthNum();
        this.bonusNum = winningLotteryBaseInfoVo.getBonusNum();
    }
}
