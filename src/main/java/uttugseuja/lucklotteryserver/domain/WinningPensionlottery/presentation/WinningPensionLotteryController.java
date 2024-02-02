package uttugseuja.lucklotteryserver.domain.WinningPensionlottery.presentation;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.service.WinningPensionLotteryService;
import uttugseuja.lucklotteryserver.global.error.exception.LuckLotteryIoException;


@RestController
@RequestMapping("/api/v1/winning")
@RequiredArgsConstructor
@Slf4j
public class WinningPensionLotteryController {

    private final WinningPensionLotteryService winningPensionLotteryService;

    @PostMapping("/save/db")
    public void InsetDb(@RequestParam("start") Integer start, @RequestParam("end") Integer end) throws LuckLotteryIoException {
        winningPensionLotteryService.saveWinningPensionLottery(start,end);
    }

}
