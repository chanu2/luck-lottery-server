package uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.response;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class OneRoundResponse {

    private Integer round;

    private LocalDate winningDate;

    private List<LotteryResponse> lotteryResponses;

    private WinningLotteryNumbersResponse winningLotteryNumbersResponse;

    public OneRoundResponse(Integer round,
                            LocalDate winningDate,
                            List<LotteryResponse> lotteryResponses,
                            WinningLotteryNumbersResponse winningLotteryNumbersResponse) {
        this.round = round;
        this.winningDate = winningDate;
        this.lotteryResponses = lotteryResponses;
        this.winningLotteryNumbersResponse = winningLotteryNumbersResponse;
    }
}
