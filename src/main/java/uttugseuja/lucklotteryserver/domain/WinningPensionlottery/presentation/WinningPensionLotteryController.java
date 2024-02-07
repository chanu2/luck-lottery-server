package uttugseuja.lucklotteryserver.domain.WinningPensionlottery.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.dto.response.WinningPensionLotteryResponse;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.service.WinningPensionLotteryService;
import uttugseuja.lucklotteryserver.global.error.exception.LuckLotteryIoException;

@RestController
@RequestMapping("/api/v1/winning/pension/lottery")
@RequiredArgsConstructor
@Slf4j
public class WinningPensionLotteryController {

    private final WinningPensionLotteryService winningPensionLotteryService;

    @PostMapping("/save/db")
    public void InsetDb(@RequestParam("start") Integer start, @RequestParam("end") Integer end) throws LuckLotteryIoException {
        winningPensionLotteryService.saveWinningPensionLottery(start,end);
    }

    @Operation(summary = "홈화면 제공")
    @GetMapping("/home")
    public WinningPensionLotteryResponse getWinningLottery() {
        return winningPensionLotteryService.recentWinningPensionLottery();
    }

}
