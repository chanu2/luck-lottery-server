package uttugseuja.lucklotteryserver.domain.WinningPensionlottery.domain.vo;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@Builder
public class WinningPensionLotteryBaseInfoVo {

    private Long winningPensionLotteryId;
    private Integer round;
    private LocalDate lotteryDrawTime;
    private Integer lotteryGroup;
    private Integer winningFirstNum;
    private Integer winningSecondNum;
    private Integer winningThirdNum;
    private Integer winningFourthNum;
    private Integer winningFifthNum;
    private Integer winningSixthNum;
    private Integer bonusFirstNum;
    private Integer bonusSecondNum;
    private Integer bonusThirdNum;
    private Integer bonusFourthNum;
    private Integer bonusFifthNum;
    private Integer bonusSixthNum;
}

