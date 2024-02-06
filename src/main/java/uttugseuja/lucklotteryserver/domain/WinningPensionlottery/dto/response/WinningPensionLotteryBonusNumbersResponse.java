package uttugseuja.lucklotteryserver.domain.WinningPensionlottery.dto.response;

import lombok.Getter;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.domain.vo.WinningPensionLotteryBaseInfoVo;

@Getter
public class WinningPensionLotteryBonusNumbersResponse {

    private Integer bonusFirstNum;
    private Integer bonusSecondNum;
    private Integer bonusThirdNum;
    private Integer bonusFourthNum;
    private Integer bonusFifthNum;
    private Integer bonusSixthNum;

    public WinningPensionLotteryBonusNumbersResponse(WinningPensionLotteryBaseInfoVo winningPensionLotteryBaseInfoVo) {
        bonusFirstNum = winningPensionLotteryBaseInfoVo.getBonusFirstNum();
        bonusSecondNum = winningPensionLotteryBaseInfoVo.getBonusSecondNum();
        bonusThirdNum = winningPensionLotteryBaseInfoVo.getBonusThirdNum();
        bonusFourthNum = winningPensionLotteryBaseInfoVo.getBonusFourthNum();
        bonusFifthNum = winningPensionLotteryBaseInfoVo.getBonusFifthNum();
        bonusSixthNum = winningPensionLotteryBaseInfoVo.getBonusSixthNum();
    }
}
