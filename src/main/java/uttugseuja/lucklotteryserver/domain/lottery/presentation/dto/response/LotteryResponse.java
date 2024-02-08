package uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.response;

import lombok.Getter;
import uttugseuja.lucklotteryserver.domain.lottery.domain.vo.LotteryBaseInfoVo;
import uttugseuja.lucklotteryserver.global.common.Rank;

import java.util.List;

@Getter
public class LotteryResponse {

    private Integer firstNum;

    private Integer secondNum;

    private Integer thirdNum;

    private Integer fourthNum;

    private Integer fifthNum;

    private Integer sixthNum;

    private Rank rank;

    private List<Integer> correctNumbers;

    public LotteryResponse(LotteryBaseInfoVo lotteryBaseInfoVo, List<Integer> correctNumbers) {
        this.firstNum = lotteryBaseInfoVo.getFirstNum();
        this.secondNum = lotteryBaseInfoVo.getSecondNum();
        this.thirdNum = lotteryBaseInfoVo.getThirdNum();
        this.fourthNum = lotteryBaseInfoVo.getFourthNum();
        this.fifthNum = lotteryBaseInfoVo.getFifthNum();
        this.sixthNum = lotteryBaseInfoVo.getSixthNum();
        this.rank = lotteryBaseInfoVo.getRank();
        this.correctNumbers = correctNumbers;
    }
}
