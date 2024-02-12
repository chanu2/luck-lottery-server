package uttugseuja.lucklotteryserver.domain.WinningPensionlottery.dto.response;

import lombok.Getter;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.domain.vo.WinningPensionLotteryBaseInfoVo;
import java.time.LocalDate;


@Getter
public class WinningPensionLotteryResponse {

    private Integer round;
    private LocalDate winningDate;
    private Integer lotteryGroup;
    private Integer winningFirstNum;
    private Integer winningSecondNum;
    private Integer winningThirdNum;
    private Integer winningFourthNum;
    private Integer winningFifthNum;
    private Integer winningSixthNum;
    private Integer bonusFirstNum;
    private Integer bonusSecondNum;
    private Integer bonusThirdNum;
    private Integer bonusFourthNum;
    private Integer bonusFifthNum;
    private Integer bonusSixthNum;

    public WinningPensionLotteryResponse(WinningPensionLotteryBaseInfoVo winningPensionLotteryBaseInfoVo) {
        round = winningPensionLotteryBaseInfoVo.getRound();
        winningDate = winningPensionLotteryBaseInfoVo.getLotteryDrawTime();
        lotteryGroup = winningPensionLotteryBaseInfoVo.getLotteryGroup();
        winningFirstNum = winningPensionLotteryBaseInfoVo.getWinningFirstNum();
        winningSecondNum = winningPensionLotteryBaseInfoVo.getWinningSecondNum();
        winningThirdNum = winningPensionLotteryBaseInfoVo.getWinningThirdNum();
        winningFourthNum = winningPensionLotteryBaseInfoVo.getWinningFourthNum();
        winningFifthNum = winningPensionLotteryBaseInfoVo.getWinningFifthNum();
        winningSixthNum = winningPensionLotteryBaseInfoVo.getWinningSixthNum();
        bonusFirstNum = winningPensionLotteryBaseInfoVo.getBonusFirstNum();
        bonusSecondNum = winningPensionLotteryBaseInfoVo.getBonusFirstNum();
        bonusThirdNum = winningPensionLotteryBaseInfoVo.getBonusFirstNum();
        bonusFourthNum = winningPensionLotteryBaseInfoVo.getBonusFirstNum();
        bonusFifthNum = winningPensionLotteryBaseInfoVo.getBonusFirstNum();
        bonusSixthNum = winningPensionLotteryBaseInfoVo.getBonusFirstNum();
    }
}
