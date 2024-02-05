package uttugseuja.lucklotteryserver.domain.winning_lottery.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uttugseuja.lucklotteryserver.domain.winning_lottery.service.WinningLotteryService;
import uttugseuja.lucklotteryserver.global.time.TimeTrace;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/winning/lottery")
public class WinningLotteryController {

    private final WinningLotteryService winningLotteryService;

    @PostMapping("/save/all")
    @TimeTrace
    public void saveWinningLotteriesOpenApi() {
        winningLotteryService.saveWinningLotteriesOpenApi();
    }
}
