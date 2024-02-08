package uttugseuja.lucklotteryserver.domain.lottery.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;
import uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.request.CreateLotteryRequest;
import uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.response.OneRoundResponse;
import uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.response.RandomLotteryResponse;
import uttugseuja.lucklotteryserver.domain.lottery.service.LotteryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lottery")
@Tag(name = "사용자 로또 관련 Controller", description = "사용자 로또 저장 및 조회 관련 담당")
public class LotteryController {

    private final LotteryService lotteryService;

    @GetMapping("/random")
    @Operation(summary = "랜덤 로또 번호 생성 API")
    public RandomLotteryResponse createRandomLottery() {
        return lotteryService.createRandomLottery();
    }

    @PostMapping("/save")
    @Operation(summary = "사용자 로또 번호 저장 API")
    public void saveLottery(@Valid @RequestBody CreateLotteryRequest createLotteryRequest) {
        lotteryService.saveLottery(createLotteryRequest);
    }

    @GetMapping("/get")
    @Operation(summary = "회차별 사용자 로또 목록 조회 API")
    @Parameters({
            @Parameter(name = "page", description = "Page number", example = "0", required = false),
            @Parameter(name = "size", description = "Page size", example = "10", required = false)
    })
    public Slice<OneRoundResponse> getLotteries(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                @RequestParam(value = "size", defaultValue = "10") Integer size) {

        PageRequest pageRequest = PageRequest.of(page, size);

        return lotteryService.getLotteriesByUser(pageRequest);
    }
}
