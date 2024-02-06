package uttugseuja.lucklotteryserver.domain.winning_lottery.service;

import java.time.LocalDate;

public interface WinningLotteryUtils {
    int getRecentRound();

    LocalDate getWinningDate(Integer round);
}
