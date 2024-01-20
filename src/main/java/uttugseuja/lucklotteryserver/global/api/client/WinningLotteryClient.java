package uttugseuja.lucklotteryserver.global.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "WinningLotteryClient", url = "https://www.dhlottery.co.kr/common.do")
public interface WinningLotteryClient {

    @GetMapping()
    String getWinningLotteryInfo(@RequestParam(value = "method") String method,
                                          @RequestParam(value = "drwNo") Integer drwNo);
}
