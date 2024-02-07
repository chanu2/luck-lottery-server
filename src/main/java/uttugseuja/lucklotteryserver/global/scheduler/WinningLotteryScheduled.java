package uttugseuja.lucklotteryserver.global.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uttugseuja.lucklotteryserver.domain.winning_lottery.service.WinningLotteryUtils;

@RequiredArgsConstructor
@Service
public class WinningLotteryScheduled {

    private final WinningLotteryUtils winningLotteryUtils;

    @Scheduled(cron = "0 0 21 * * 6")
    public void processScheduledWinningLottery() {
        winningLotteryUtils.updateWinningLottery();
    }

}
