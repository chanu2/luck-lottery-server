package uttugseuja.lucklotteryserver.domain.pensionlottery.presentation.dto.response;

import lombok.Getter;
import uttugseuja.lucklotteryserver.domain.pensionlottery.domain.vo.PensionLotteryBaseInfoVo;

@Getter
public class PensionLotteryNumbersResponse {

    private Integer pensionGroup;
    private Integer pensionFirstNum;
    private Integer pensionSecondNum;
    private Integer pensionThirdNum;
    private Integer pensionFourthNum;
    private Integer pensionFifthNum;
    private Integer pensionSixthNum;

    public PensionLotteryNumbersResponse(PensionLotteryBaseInfoVo pensionLotteryBaseInfoVo) {
        pensionGroup = pensionLotteryBaseInfoVo.getPensionGroup();
        pensionFirstNum = pensionLotteryBaseInfoVo.getPensionFirstNum();
        pensionSecondNum = pensionLotteryBaseInfoVo.getPensionSecondNum();
        pensionThirdNum = pensionLotteryBaseInfoVo.getPensionThirdNum();
        pensionFourthNum = pensionLotteryBaseInfoVo.getPensionFourthNum();
        pensionFifthNum = pensionLotteryBaseInfoVo.getPensionFifthNum();
        pensionSixthNum = pensionLotteryBaseInfoVo.getPensionSixthNum();
    }
}
