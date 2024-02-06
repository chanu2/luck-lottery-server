package uttugseuja.lucklotteryserver.domain.pensionlottery.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uttugseuja.lucklotteryserver.domain.pensionlottery.presentation.dto.request.CreatePensionLotteryRequest;
import uttugseuja.lucklotteryserver.domain.pensionlottery.presentation.dto.response.PensionLotteryResponse;
import uttugseuja.lucklotteryserver.domain.pensionlottery.presentation.dto.response.RandomPensionLotteryResponse;
import uttugseuja.lucklotteryserver.domain.pensionlottery.service.PensionLotteryService;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pension/lottery")
public class PensionLotteryController {

    private final PensionLotteryService pensionLotteryService;

    @GetMapping("/random")
    public RandomPensionLotteryResponse createRandomLottery() {
        return pensionLotteryService.createRandomPensionLottery();
    }

    @PostMapping("/save")
    public void savePensionLottery(@RequestBody CreatePensionLotteryRequest createPensionLotteryRequest) {
        pensionLotteryService.savePensionLottery(createPensionLotteryRequest);
    }

    @GetMapping("/get")
    public List<PensionLotteryResponse> getLotteries() {
        return pensionLotteryService.getPensionLottery();
    }

}
