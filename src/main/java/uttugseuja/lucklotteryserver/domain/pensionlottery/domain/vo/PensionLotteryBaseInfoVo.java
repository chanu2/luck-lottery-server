package uttugseuja.lucklotteryserver.domain.pensionlottery.domain.vo;

import lombok.Builder;
import lombok.Getter;
import uttugseuja.lucklotteryserver.global.common.Rank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class PensionLotteryBaseInfoVo {

    private final Long pensionLotteryId;
    private Boolean checkWinningBonus;
    private final Integer pensionRound;
    private final LocalDate winningDate;
    private final Integer pensionGroup;
    private final Integer pensionFirstNum;
    private final Integer pensionSecondNum;
    private final Integer pensionThirdNum;
    private final Integer pensionFourthNum;
    private final Integer pensionFifthNum;
    private final Integer pensionSixthNum;
    private final Rank rank;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifyDate;
}

