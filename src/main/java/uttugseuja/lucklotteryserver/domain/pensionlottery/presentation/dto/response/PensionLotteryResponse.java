package uttugseuja.lucklotteryserver.domain.pensionlottery.presentation.dto.response;

import lombok.Getter;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.dto.response.WinningPensionLotteryBonusNumbersResponse;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.dto.response.WinningPensionLotteryNumbersResponse;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PensionLotteryResponse {

    private Integer round;
    private LocalDateTime winningDate;
    private List<PensionLotteryNumbersResponse> pensionLotteryNumbersResponse;
    private WinningPensionLotteryNumbersResponse winningLotteryNumbersResponse;
    private WinningPensionLotteryBonusNumbersResponse winningPensionLotteryBonusNumbersResponse;

    public PensionLotteryResponse(Integer round,
                                  LocalDateTime winningDate,
                                  List<PensionLotteryNumbersResponse> pensionLotteryNumbersResponse,
                                  WinningPensionLotteryNumbersResponse winningLotteryNumbersResponse,
                                  WinningPensionLotteryBonusNumbersResponse winningPensionLotteryBonusNumbersResponse

                                  ) {

        this.round = round;
        this.winningDate = winningDate;
        this.pensionLotteryNumbersResponse = pensionLotteryNumbersResponse;
        this.winningLotteryNumbersResponse = winningLotteryNumbersResponse;
        this.winningPensionLotteryBonusNumbersResponse = winningPensionLotteryBonusNumbersResponse;

    }
}
