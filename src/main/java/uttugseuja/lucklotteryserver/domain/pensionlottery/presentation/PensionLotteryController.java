package uttugseuja.lucklotteryserver.domain.pensionlottery.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import uttugseuja.lucklotteryserver.domain.pensionlottery.presentation.dto.request.CreatePensionLotteryRequest;
import uttugseuja.lucklotteryserver.domain.pensionlottery.presentation.dto.response.PensionLotteryResponse;
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

    @PostMapping("/save")
    public void savePensionLottery(@Valid @RequestBody CreatePensionLotteryRequest createPensionLotteryRequest) {
        pensionLotteryService.savePensionLottery(createPensionLotteryRequest);
    }

    @Operation(summary = "나의 연금복권 조회")
    @GetMapping("/get")
    @Parameters({
            @Parameter(name = "page", description = "Page number", example = "0", required = false),
            @Parameter(name = "size", description = "Page size", example = "10", required = false)
    })
    public Slice<PensionLotteryResponse> getLotteries(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {

        PageRequest pageRequest = PageRequest.of(page,size, Sort.Direction.DESC,"pensionRound");
        return pensionLotteryService.getPensionLottery(pageRequest);
    }

}
