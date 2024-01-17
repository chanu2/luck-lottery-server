package uttugseuja.lucklotteryserver.domain.winningLottery.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
