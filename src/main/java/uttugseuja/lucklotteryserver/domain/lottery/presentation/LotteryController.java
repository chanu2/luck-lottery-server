package uttugseuja.lucklotteryserver.domain.lottery.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.response.LotteryResponse;
import uttugseuja.lucklotteryserver.domain.lottery.service.LotteryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lottery")
public class LotteryController {

    private final LotteryService lotteryService;

    @PostMapping("/random")
    public LotteryResponse createRandomLottery() {
        return lotteryService.createRandomLottery();
    }
}
