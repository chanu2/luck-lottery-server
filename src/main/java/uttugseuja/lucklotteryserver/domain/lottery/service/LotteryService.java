package uttugseuja.lucklotteryserver.domain.lottery.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uttugseuja.lucklotteryserver.global.api.client.WinningLotteryClient;
import uttugseuja.lucklotteryserver.global.api.dto.WinningLotteryDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class LotteryService {

    private final WinningLotteryClient winningLotteryClient;
    private static final String method = "getLottoNumber";

    public WinningLotteryDto getWinningLottery(Integer round) throws JsonProcessingException {
        String json = winningLotteryClient.getWinningLotteryInfo(method, round);
        return deserialization(json);
    }

    private WinningLotteryDto deserialization(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, WinningLotteryDto.class);
    }
}
