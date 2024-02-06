package uttugseuja.lucklotteryserver.domain.lottery.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.request.CreateLotteryRequest;
import uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.response.LotteryResponse;
import uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.response.RandomLotteryResponse;
import uttugseuja.lucklotteryserver.domain.lottery.service.LotteryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lottery")
@Tag(name = "사용자 로또 관련 Controller", description = "사용자 로또 저장 및 조회 관련 담당")
public class LotteryController {

    private final LotteryService lotteryService;

    @GetMapping("/random")
    public RandomLotteryResponse createRandomLottery() {
        return lotteryService.createRandomLottery();
    }

    @PostMapping("/save")
    @Operation(summary = "사용자 로또 번호 저장 API")
    public void saveLottery(@Valid @RequestBody CreateLotteryRequest createLotteryRequest) {
        lotteryService.saveLottery(createLotteryRequest);
    }

    @GetMapping("/get")
    public List<LotteryResponse> getLotteries() {
        return lotteryService.getLotteriesByUser();
    }
}
