package uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.response;

import lombok.Getter;
import uttugseuja.lucklotteryserver.domain.winning_lottery.presentation.dto.response.WinningLotteryNumbersResponse;

import java.time.LocalDate;
import java.util.List;

@Getter
public class LotteryResponse {

    private Integer round;

    private LocalDate winningDate;

    private List<LotteryNumbersResponse> lotteryNumbersResponse;

    private WinningLotteryNumbersResponse winningLotteryNumbersResponse;

    public LotteryResponse(Integer round,
                           LocalDate winningDate,
                           List<LotteryNumbersResponse> lotteryNumbersResponse,
                           WinningLotteryNumbersResponse winningLotteryNumbersResponse) {
        this.round = round;
        this.winningDate = winningDate;
        this.lotteryNumbersResponse = lotteryNumbersResponse;
        this.winningLotteryNumbersResponse = winningLotteryNumbersResponse;
    }
}
