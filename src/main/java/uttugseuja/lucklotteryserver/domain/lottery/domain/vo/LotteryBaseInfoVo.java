package uttugseuja.lucklotteryserver.domain.lottery.domain.vo;

import lombok.Builder;
import lombok.Getter;
import uttugseuja.lucklotteryserver.global.common.Rank;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class LotteryBaseInfoVo {

    private final Long lotteryId;

    private final Integer round;

    private final LocalDate winningDate;

    private final Integer firstNum;

    private final Integer secondNum;

    private final Integer thirdNum;

    private final Integer fourthNum;

    private final Integer fifthNum;

    private final Integer sixthNum;

    private final Rank rank;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifyDate;
}

