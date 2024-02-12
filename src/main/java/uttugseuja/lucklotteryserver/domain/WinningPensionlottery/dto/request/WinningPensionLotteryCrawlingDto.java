package uttugseuja.lucklotteryserver.domain.WinningPensionlottery.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class WinningPensionLotteryCrawlingDto {

    private Integer pensionLotteryRound;
    private LocalDate lotteryDrawTime;
    private List<Integer> winningNumbers;

}
