package uttugseuja.lucklotteryserver.domain.WinningPensionlottery.dto.response;

import lombok.Getter;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.domain.vo.WinningPensionLotteryBaseInfoVo;

@Getter
public class WinningPensionLotteryNumbersResponse {

    private Integer lotteryGroup;
    private Integer winningFirstNum;
    private Integer winningSecondNum;
    private Integer winningThirdNum;
    private Integer winningFourthNum;
    private Integer winningFifthNum;
    private Integer winningSixthNum;

    public WinningPensionLotteryNumbersResponse(WinningPensionLotteryBaseInfoVo winningPensionLotteryBaseInfoVo) {
        lotteryGroup = winningPensionLotteryBaseInfoVo.getLotteryGroup();
        winningFirstNum = winningPensionLotteryBaseInfoVo.getWinningFirstNum();
        winningSecondNum = winningPensionLotteryBaseInfoVo.getWinningSecondNum();
        winningThirdNum = winningPensionLotteryBaseInfoVo.getWinningThirdNum();
        winningFourthNum = winningPensionLotteryBaseInfoVo.getWinningFourthNum();
        winningFifthNum = winningPensionLotteryBaseInfoVo.getWinningFifthNum();
        winningSixthNum = winningPensionLotteryBaseInfoVo.getWinningSixthNum();

    }
}
