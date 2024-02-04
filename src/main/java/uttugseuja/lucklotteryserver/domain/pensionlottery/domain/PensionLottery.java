package uttugseuja.lucklotteryserver.domain.pensionlottery.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uttugseuja.lucklotteryserver.domain.user.domain.User;
import uttugseuja.lucklotteryserver.global.common.Rank;
import uttugseuja.lucklotteryserver.global.database.BaseEntity;
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
                   Integer pensionRound,
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
        this.pensionRound = pensionRound;
        this.pensionGroup = pensionGroup;
        this.pensionFirstNum = pensionFirstNum;
        this.pensionSecondNum = pensionSecondNum;
        this.pensionThirdNum = pensionThirdNum;
        this.pensionFourthNum = pensionFourthNum;
        this.pensionFifthNum = pensionFifthNum;
        this.pensionSixthNum = pensionSixthNum;
    }

}
