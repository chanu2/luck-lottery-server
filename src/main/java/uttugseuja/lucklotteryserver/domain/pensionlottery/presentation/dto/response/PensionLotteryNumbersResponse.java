package uttugseuja.lucklotteryserver.domain.pensionlottery.presentation.dto.response;

import lombok.Getter;
import uttugseuja.lucklotteryserver.domain.pensionlottery.domain.vo.PensionLotteryBaseInfoVo;
import uttugseuja.lucklotteryserver.global.common.Rank;

import java.util.List;

@Getter
public class PensionLotteryNumbersResponse {

    private Integer pensionGroup;
    private Integer pensionFirstNum;
    private Integer pensionSecondNum;
    private Integer pensionThirdNum;
    private Integer pensionFourthNum;
    private Integer pensionFifthNum;
    private Integer pensionSixthNum;
    private Rank rank;
    private Boolean checkWinningBonus;
    private List<Boolean> correctNumbers;
    private List<Boolean> bonusCorrectNumbers;

    public PensionLotteryNumbersResponse(PensionLotteryBaseInfoVo pensionLotteryBaseInfoVo,List<Boolean> checkNumbers,List<Boolean> checkBonusNumbers) {
        this.pensionGroup = pensionLotteryBaseInfoVo.getPensionGroup();
        this.pensionFirstNum = pensionLotteryBaseInfoVo.getPensionFirstNum();
        this.pensionSecondNum = pensionLotteryBaseInfoVo.getPensionSecondNum();
        this.pensionThirdNum = pensionLotteryBaseInfoVo.getPensionThirdNum();
        this.pensionFourthNum = pensionLotteryBaseInfoVo.getPensionFourthNum();
        this.pensionFifthNum = pensionLotteryBaseInfoVo.getPensionFifthNum();
        this.pensionSixthNum = pensionLotteryBaseInfoVo.getPensionSixthNum();
        this.rank = pensionLotteryBaseInfoVo.getRank();
        this.checkWinningBonus = pensionLotteryBaseInfoVo.getCheckWinningBonus();
        this.correctNumbers = checkNumbers;
        this.bonusCorrectNumbers = checkBonusNumbers;
    }
}
