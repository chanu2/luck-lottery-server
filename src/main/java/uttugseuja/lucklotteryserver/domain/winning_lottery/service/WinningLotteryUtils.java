package uttugseuja.lucklotteryserver.domain.winning_lottery.service;

import uttugseuja.lucklotteryserver.domain.winning_lottery.domain.WinningLottery;

public interface WinningLotteryUtils {
    int getRecentRound();

    WinningLottery getWinningLottery(Integer round);

    void updateWinningLottery();
}
