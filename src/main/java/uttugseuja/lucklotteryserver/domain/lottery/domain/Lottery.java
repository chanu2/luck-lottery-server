package uttugseuja.lucklotteryserver.domain.lottery.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uttugseuja.lucklotteryserver.domain.lottery.domain.vo.LotteryBaseInfoVo;
import uttugseuja.lucklotteryserver.domain.user.domain.User;
import uttugseuja.lucklotteryserver.global.common.Rank;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Lottery {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "lottery_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Rank rank;

    private Integer round;
    private LocalDate winningDate;
    private Integer firstNum;
    private Integer secondNum;
    private Integer thirdNum;
    private Integer fourthNum;
    private Integer fifthNum;
    private Integer sixthNum;

    @Builder
    public Lottery(Integer round,
                   LocalDate winningDate,
                   Integer firstNum,
                   Integer secondNum,
                   Integer thirdNum,
                   Integer fourthNum,
                   Integer fifthNum,
                   Integer sixthNum) {
        this.round = round;
        this.winningDate = winningDate;
        this.firstNum = firstNum;
        this.secondNum = secondNum;
        this.thirdNum = thirdNum;
        this.fourthNum = fourthNum;
        this.fifthNum = fifthNum;
        this.sixthNum = sixthNum;
    }

    public LotteryBaseInfoVo getLotteryBaseInfoVo() {
        return LotteryBaseInfoVo.builder()
                .lotteryId(id)
                .round(round)
                .winningDate(winningDate)
                .firstNum(firstNum)
                .secondNum(secondNum)
                .thirdNum(thirdNum)
                .fourthNum(fourthNum)
                .fifthNum(fifthNum)
                .sixthNum(sixthNum)
                .build();
    }
}
