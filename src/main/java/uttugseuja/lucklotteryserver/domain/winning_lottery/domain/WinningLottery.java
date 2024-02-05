package uttugseuja.lucklotteryserver.domain.winning_lottery.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uttugseuja.lucklotteryserver.domain.winning_lottery.domain.vo.WinningLotteryBaseInfoVo;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class WinningLottery {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "winning_lottery_id")
    private Long id;

    private Integer round;
    private LocalDate winningDate;
    private Integer firstNum;
    private Integer secondNum;
    private Integer thirdNum;
    private Integer fourthNum;
    private Integer fifthNum;
    private Integer sixthNum;
    private Integer bonusNum;

    @Builder
    public WinningLottery(Integer round,
                   LocalDate winningDate,
                   Integer firstNum,
                   Integer secondNum,
                   Integer thirdNum,
                   Integer fourthNum,
                   Integer fifthNum,
                   Integer sixthNum,
                   Integer bonusNum) {
        this.round = round;
        this.winningDate = winningDate;
        this.firstNum = firstNum;
        this.secondNum = secondNum;
        this.thirdNum = thirdNum;
        this.fourthNum = fourthNum;
        this.fifthNum = fifthNum;
        this.sixthNum = sixthNum;
        this.bonusNum = bonusNum;
    }

    public WinningLotteryBaseInfoVo getWinningLotteryBaseInfoVo() {
        return WinningLotteryBaseInfoVo.builder()
                .winningLotteryId(id)
                .round(round)
                .winningDate(winningDate)
                .firstNum(firstNum)
                .secondNum(secondNum)
                .thirdNum(thirdNum)
                .fourthNum(fourthNum)
                .fifthNum(fifthNum)
                .sixthNum(sixthNum)
                .bonusNum(bonusNum)
                .build();
    }

}
