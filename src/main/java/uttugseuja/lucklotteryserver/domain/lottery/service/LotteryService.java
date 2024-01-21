package uttugseuja.lucklotteryserver.domain.lottery.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uttugseuja.lucklotteryserver.domain.lottery.exception.BadRoundException;
import uttugseuja.lucklotteryserver.global.api.client.WinningLotteryClient;
import uttugseuja.lucklotteryserver.global.api.dto.WinningLotteryDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class LotteryService {

    private final WinningLotteryClient winningLotteryClient;
    private static final String method = "getLottoNumber";

    public WinningLotteryDto getWinningLottery(Integer round) {
        String json = winningLotteryClient.getWinningLotteryInfo(method, round);
        return deserialization(json);
    }

    private WinningLotteryDto deserialization(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            return objectMapper.readValue(json, WinningLotteryDto.class);
        } catch (JsonProcessingException e) {
            throw BadRoundException.EXCEPTION;
        }
    }
}
