package uttugseuja.lucklotteryserver.domain.winning_lottery.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uttugseuja.lucklotteryserver.domain.winning_lottery.presentation.dto.response.WinningLotteryResponse;
import uttugseuja.lucklotteryserver.domain.winning_lottery.service.WinningLotteryService;
import uttugseuja.lucklotteryserver.global.time.TimeTrace;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/winning/lottery")
@Tag(name = "당첨 로또 관련 Controller", description = "당첨 로또 저장 및 조회 관련 기능 담당")
public class WinningLotteryController {

    private final WinningLotteryService winningLotteryService;

    @Operation(summary = "1회차부터 최근 회차까지의 당첨 로또 번호 DB에 저장")
    @PostMapping("/save/all")
    @TimeTrace
    public void saveWinningLotteriesOpenApi() {
        winningLotteryService.saveWinningLotteriesOpenApi();
    }

    @Operation(summary = "최근 회차의 당첨 로또 정보 조회(홈 페이지용)")
    @GetMapping("/recent/round")
    public WinningLotteryResponse recentRoundWinningLottery() {
        return winningLotteryService.getRecentRoundWinningLottery();
    }
}
