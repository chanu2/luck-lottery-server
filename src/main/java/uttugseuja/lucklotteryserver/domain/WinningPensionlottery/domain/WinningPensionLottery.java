package uttugseuja.lucklotteryserver.domain.WinningPensionlottery.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.domain.vo.WinningPensionLotteryBaseInfoVo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class WinningPensionLottery {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "winning_pension_lottery_id")
    private Long id;

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

    @Builder
    public WinningPensionLottery(Long id,
                                 Integer round,
                                 LocalDate lotteryDrawTime,
                                 Integer lotteryGroup,
                                 Integer winningFirstNum,
                                 Integer winningSecondNum,
                                 Integer winningThirdNum,
                                 Integer winningFourthNum,
                                 Integer winningFifthNum,
                                 Integer winningSixthNum,
                                 Integer bonusFirstNum,
                                 Integer bonusSecondNum,
                                 Integer bonusThirdNum,
                                 Integer bonusFourthNum,
                                 Integer bonusFifthNum,
                                 Integer bonusSixthNum) {
        this.id =id;
        this.lotteryDrawTime = lotteryDrawTime;
        this.round = round;
        this.lotteryGroup = lotteryGroup;
        this.winningFirstNum = winningFirstNum;
        this.winningSecondNum = winningSecondNum;
        this.winningThirdNum = winningThirdNum;
        this.winningFourthNum = winningFourthNum;
        this.winningFifthNum = winningFifthNum;
        this.winningSixthNum = winningSixthNum;
        this.bonusFirstNum = bonusFirstNum;
        this.bonusSecondNum = bonusSecondNum;
        this.bonusThirdNum = bonusThirdNum;
        this.bonusFourthNum = bonusFourthNum;
        this.bonusFifthNum = bonusFifthNum;
        this.bonusSixthNum = bonusSixthNum;
    }

    public WinningPensionLotteryBaseInfoVo getWinningPensionLotteryBaseInfoVo() {
        return WinningPensionLotteryBaseInfoVo.builder()
                .winningPensionLotteryId(id)
                .round(round)
                .lotteryDrawTime(lotteryDrawTime)
                .lotteryGroup(lotteryGroup)
                .winningFirstNum(winningFirstNum)
                .winningSecondNum(winningSecondNum)
                .winningThirdNum(winningThirdNum)
                .winningFourthNum(winningFourthNum)
                .winningFifthNum(winningFifthNum)
                .winningSixthNum(winningSixthNum)
                .bonusFirstNum(bonusFirstNum)
                .bonusSecondNum(bonusSecondNum)
                .bonusThirdNum(bonusThirdNum)
                .bonusFourthNum(bonusFourthNum)
                .bonusFifthNum(bonusFifthNum)
                .bonusSixthNum(bonusSixthNum)
                .build();
    }

    public List<Integer> getWinningNumbers() {

        List<Integer> winningNumbers = new ArrayList<>();
        winningNumbers.add(getLotteryGroup());
        winningNumbers.add(getWinningFirstNum());
        winningNumbers.add(getWinningSecondNum());
        winningNumbers.add(getWinningThirdNum());
        winningNumbers.add(getWinningFourthNum());
        winningNumbers.add(getWinningFifthNum());
        winningNumbers.add(getWinningSixthNum());
        return winningNumbers;
    }

    public List<Integer> getWinningBonusNumbers() {

        List<Integer> bonusNumbers = new ArrayList<>();
        bonusNumbers.add(getBonusFirstNum());
        bonusNumbers.add(getBonusSecondNum());
        bonusNumbers.add(getBonusThirdNum());
        bonusNumbers.add(getBonusFourthNum());
        bonusNumbers.add(getBonusFifthNum());
        bonusNumbers.add(getBonusSixthNum());
        return bonusNumbers;
    }

}
