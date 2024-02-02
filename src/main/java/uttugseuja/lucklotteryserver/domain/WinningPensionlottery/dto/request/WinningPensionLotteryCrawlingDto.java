package uttugseuja.lucklotteryserver.domain.WinningPensionlottery.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class WinningPensionLotteryCrawlingDto {

    private Integer pensionLotteryRound;
    private LocalDateTime lotteryDrawTime;
    private List<Integer> winningNumbers;

}
