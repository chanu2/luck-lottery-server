package uttugseuja.lucklotteryserver.domain.lottery.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
}
