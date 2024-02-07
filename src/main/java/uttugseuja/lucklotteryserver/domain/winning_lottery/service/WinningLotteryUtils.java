package uttugseuja.lucklotteryserver.domain.winning_lottery.service;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public interface WinningLotteryUtils {
    int getRecentRound();

    LocalDate getWinningDate(Integer round);

    void updateWinningLottery();
}
