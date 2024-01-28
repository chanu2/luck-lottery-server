package uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.response;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import uttugseuja.lucklotteryserver.domain.lottery.domain.vo.LotteryBaseInfoVo;
import uttugseuja.lucklotteryserver.global.common.Rank;

import java.time.LocalDate;
import java.util.List;

@Getter
public class LotteryResponse {

    private Integer round;

    private LocalDate winningDate;

    private LotteryNumbersResponse lotteryNumbersResponse;

    private WinningLotteryNumbersResponse winningLotteryNumbersResponse;

    private List<Integer> correctNumbers;

    private Rank rank;

    public LotteryResponse(LotteryNumbersResponse lotteryNumbersResponse,
                           WinningLotteryNumbersResponse winningLotteryNumbersResponse,
                           List<Integer> correctNumbers,
                           LotteryBaseInfoVo lotteryBaseInfoVo) {
        this.round = lotteryBaseInfoVo.getRound();
        this.winningDate = lotteryBaseInfoVo.getWinningDate();
        this.lotteryNumbersResponse = lotteryNumbersResponse;
        this.winningLotteryNumbersResponse = winningLotteryNumbersResponse;
        this.correctNumbers = correctNumbers;
        this.rank = lotteryBaseInfoVo.getRank();
    }
}
