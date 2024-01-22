package uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.response;

import lombok.Getter;
import uttugseuja.lucklotteryserver.domain.lottery.domain.vo.LotteryBaseInfoVo;

import java.time.LocalDate;

@Getter
public class LotteryResponse {

    private Integer round;

    private LocalDate winningDate;

    private Integer firstNum;

    private Integer secondNum;

    private Integer thirdNum;

    private Integer fourthNum;

    private Integer fifthNum;

    private Integer sixthNum;

    public LotteryResponse(LotteryBaseInfoVo lotteryBaseInfoVo) {
        round = lotteryBaseInfoVo.getRound();
        winningDate = lotteryBaseInfoVo.getWinningDate();
        firstNum = lotteryBaseInfoVo.getFirstNum();
        secondNum = lotteryBaseInfoVo.getSecondNum();
        thirdNum = lotteryBaseInfoVo.getThirdNum();
        fourthNum = lotteryBaseInfoVo.getFourthNum();
        fifthNum = lotteryBaseInfoVo.getFifthNum();
        sixthNum = lotteryBaseInfoVo.getSixthNum();
    }
}
