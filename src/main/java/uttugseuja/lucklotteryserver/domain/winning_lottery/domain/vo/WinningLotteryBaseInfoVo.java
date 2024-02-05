package uttugseuja.lucklotteryserver.domain.winning_lottery.domain.vo;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class WinningLotteryBaseInfoVo {

    private final Long winningLotteryId;

    private final Integer round;

    private final LocalDate winningDate;

    private final Integer firstNum;

    private final Integer secondNum;

    private final Integer thirdNum;

    private final Integer fourthNum;

    private final Integer fifthNum;

    private final Integer sixthNum;

    private final Integer bonusNum;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifyDate;
}
