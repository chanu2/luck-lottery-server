package uttugseuja.lucklotteryserver.domain.WinningPensionlottery.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;


import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class LotteryDrawDayDto {

    private Integer round;
    private LocalDateTime lotteryDrawTime;



}
