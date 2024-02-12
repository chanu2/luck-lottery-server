package uttugseuja.lucklotteryserver.domain.WinningPensionlottery.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class LotteryDrawDayDto {

    private Integer round;
    private LocalDate lotteryDrawTime;

}
