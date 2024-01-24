package uttugseuja.lucklotteryserver.domain.lottery.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.request.CreateLotteryRequest;
import uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.response.RandomLotteryResponse;
import uttugseuja.lucklotteryserver.domain.lottery.service.LotteryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lottery")
public class LotteryController {

    private final LotteryService lotteryService;

    @GetMapping("/random")
    public RandomLotteryResponse createRandomLottery() {
        return lotteryService.createRandomLottery();
    }

    @PostMapping("/save")
    public void saveLottery(@RequestBody CreateLotteryRequest createLotteryRequest) {
        lotteryService.saveLottery(createLotteryRequest);
    }
}
