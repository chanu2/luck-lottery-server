package uttugseuja.lucklotteryserver.global.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.domain.WinningPensionLottery;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.domain.repository.WinningPensionLotteryRepository;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.service.WinningPensionLotteryService;

@Component
@RequiredArgsConstructor
@Slf4j
public class WinningPensionLotteryUpdateScheduling {

    private final WinningPensionLotteryService winningPensionLotteryService;
    private final WinningPensionLotteryRepository winningPensionLotteryRepository;

    @Scheduled(cron = "0 30/2 19-20 * * THU") // 매주 목요일 19시 30분부터 20시 30분까지 2분마다
    @Transactional
    public void getRecentWinningLotteryCrawling() {
        log.info("[Scheduling] winning pension lottery update");
        String round = "";
        WinningPensionLottery recentWinningPensionLottery = winningPensionLotteryService.getRecentWinningPensionLottery();
        WinningPensionLottery winningPensionLottery = winningPensionLotteryService.createWinningPensionLottery(round);

        if(recentWinningPensionLottery.getRound() < winningPensionLottery.getRound()){
            winningPensionLotteryRepository.save(winningPensionLottery);
        }
    }
}