package uttugseuja.lucklotteryserver.domain.WinningPensionlottery.service;

import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.domain.WinningPensionLottery;

public interface WinningPensionLotteryUtils {

     WinningPensionLottery getRecentWinningPensionLottery();

     WinningPensionLottery getRecentWinningPensionLotteryByRound(Integer round);

}
