package uttugseuja.lucklotteryserver.domain.pensionlottery.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.request.CreateLotteryRequest;
import uttugseuja.lucklotteryserver.domain.pensionlottery.presentation.dto.request.CreatePensionLotteryRequest;
import uttugseuja.lucklotteryserver.domain.pensionlottery.presentation.dto.response.RandomPensionLotteryResponse;
import uttugseuja.lucklotteryserver.domain.pensionlottery.service.PensionLotteryService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pension/lottery")
public class PensionLotteryController {

    private final PensionLotteryService pensionLotteryService;

    @GetMapping("/random")
    public RandomPensionLotteryResponse createRandomLottery() {
        return pensionLotteryService.createRandomPensionLottery();
    }

}
