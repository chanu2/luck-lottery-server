package uttugseuja.lucklotteryserver.domain.pensionlottery.presentation.dto.response;

import lombok.Getter;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.domain.vo.WinningPensionLotteryBaseInfoVo;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.dto.response.WinningPensionLotteryBonusNumbersResponse;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.dto.response.WinningPensionLotteryNumbersResponse;
import uttugseuja.lucklotteryserver.domain.pensionlottery.domain.vo.PensionLotteryBaseInfoVo;
import uttugseuja.lucklotteryserver.global.common.Rank;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PensionLotteryResponse {

    private Integer round;
    private LocalDateTime winningDate;
    private Rank rank;
    private Boolean checkWinningBonus;
    private PensionLotteryNumbersResponse pensionLotteryNumbersResponse;
    private WinningPensionLotteryNumbersResponse winningLotteryNumbersResponse;
    private WinningPensionLotteryBonusNumbersResponse winningPensionLotteryBonusNumbersResponse;
    private List<Boolean> correctNumbers;
    private List<Boolean> bonusCorrectNumbers;

    public PensionLotteryResponse(PensionLotteryNumbersResponse pensionLotteryNumbersResponse,
                                  WinningPensionLotteryNumbersResponse winningLotteryNumbersResponse,
                                  WinningPensionLotteryBonusNumbersResponse winningPensionLotteryBonusNumbersResponse,
                                  PensionLotteryBaseInfoVo pensionLotteryBaseInfoVo,
                                  List<Boolean> correctNumbers,
                                  List<Boolean> bonusCorrectNumbers) {

        this.round = pensionLotteryBaseInfoVo.getPensionRound();
        this.winningDate = pensionLotteryBaseInfoVo.getWinningDate();
        this.rank = pensionLotteryBaseInfoVo.getRank();
        this.checkWinningBonus = pensionLotteryBaseInfoVo.getCheckWinningBonus();
        this.pensionLotteryNumbersResponse = pensionLotteryNumbersResponse;
        this.winningLotteryNumbersResponse = winningLotteryNumbersResponse;
        this.winningPensionLotteryBonusNumbersResponse = winningPensionLotteryBonusNumbersResponse;
        this.correctNumbers = correctNumbers;
        this.bonusCorrectNumbers = bonusCorrectNumbers;


    }
}
