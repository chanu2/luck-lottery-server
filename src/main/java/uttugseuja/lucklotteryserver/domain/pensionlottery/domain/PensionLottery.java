package uttugseuja.lucklotteryserver.domain.pensionlottery.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uttugseuja.lucklotteryserver.domain.pensionlottery.domain.vo.PensionLotteryBaseInfoVo;
import uttugseuja.lucklotteryserver.domain.user.domain.User;
import uttugseuja.lucklotteryserver.global.common.Rank;
import uttugseuja.lucklotteryserver.global.database.BaseEntity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class PensionLottery extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "lottery_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Rank rank;

    private LocalDate winningDate;
    private Boolean checkWinningBonus;
    private Integer pensionRound;
    private Integer pensionGroup;
    private Integer pensionFirstNum;
    private Integer pensionSecondNum;
    private Integer pensionThirdNum;
    private Integer pensionFourthNum;
    private Integer pensionFifthNum;
    private Integer pensionSixthNum;

    @Builder
    public PensionLottery(
                   Long id,
                   User user,
                   Rank rank,
                   Boolean checkWinningBonus,
                   Integer pensionRound,
                   LocalDate winningDate,
                   Integer pensionGroup,
                   Integer pensionFirstNum,
                   Integer pensionSecondNum,
                   Integer pensionThirdNum,
                   Integer pensionFourthNum,
                   Integer pensionFifthNum,
                   Integer pensionSixthNum) {
        this.id =id;
        this.user = user;
        this.rank = rank;
        this.checkWinningBonus = checkWinningBonus;
        this.pensionRound = pensionRound;
        this.winningDate = winningDate;
        this.pensionGroup = pensionGroup;
        this.pensionFirstNum = pensionFirstNum;
        this.pensionSecondNum = pensionSecondNum;
        this.pensionThirdNum = pensionThirdNum;
        this.pensionFourthNum = pensionFourthNum;
        this.pensionFifthNum = pensionFifthNum;
        this.pensionSixthNum = pensionSixthNum;
    }

    public PensionLotteryBaseInfoVo getPensionLotteryBaseInfoVo() {
        return PensionLotteryBaseInfoVo.builder()
                .pensionLotteryId(id)
                .rank(rank)
                .checkWinningBonus(checkWinningBonus)
                .winningDate(winningDate)
                .pensionRound(pensionRound)
                .pensionGroup(pensionGroup)
                .pensionFirstNum(pensionFirstNum)
                .pensionSecondNum(pensionSecondNum)
                .pensionThirdNum(pensionThirdNum)
                .pensionFourthNum(pensionFourthNum)
                .pensionFifthNum(pensionFifthNum)
                .pensionSixthNum(pensionSixthNum)
                .createdDate(getCreatedDate())
                .lastModifyDate(getLastModifyDate())
                .build();
    }

    public List<Integer> getNumbers() {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(getPensionGroup());
        numbers.add(getPensionFirstNum());
        numbers.add(getPensionSecondNum());
        numbers.add(getPensionThirdNum());
        numbers.add(getPensionFourthNum());
        numbers.add(getPensionFifthNum());
        numbers.add(getPensionSixthNum());
        return numbers;
    }

    public void updateRank(Rank rank) {
        this.rank = rank;
    }

    public void updateCheckWinningBonus() {
        this.checkWinningBonus = true;
    }

}
