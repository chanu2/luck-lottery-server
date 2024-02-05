package uttugseuja.lucklotteryserver.domain.winning_lottery.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uttugseuja.lucklotteryserver.domain.lottery.exception.BadRoundException;
import uttugseuja.lucklotteryserver.domain.winning_lottery.domain.WinningLottery;
import uttugseuja.lucklotteryserver.domain.winning_lottery.domain.repository.WinningLotteryJdbcRepository;
import uttugseuja.lucklotteryserver.domain.winning_lottery.domain.repository.WinningLotteryRepository;
import uttugseuja.lucklotteryserver.global.api.client.WinningLotteryClient;
import uttugseuja.lucklotteryserver.global.api.dto.WinningLotteryDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class WinningLotteryService {

    private final WinningLotteryClient winningLotteryClient;
    private final WinningLotteryRepository winningLotteryRepository;
    private final WinningLotteryJdbcRepository winningLotteryJdbcRepository;
    private static final String method = "getLottoNumber";

    @Transactional
    public void saveWinningLotteriesOpenApi() {
        int recentRound = getRecentRound();

        List<WinningLottery> winningLotteries = getWinningLotteries(recentRound);

        saveWinningLotteriesInDB(winningLotteries);
    }

    public int getRecentRound() {
        LocalDateTime startDate = LocalDateTime.of(2002, 12, 7, 21, 0);
        LocalDateTime now = LocalDateTime.now();

        int days = (int) ChronoUnit.DAYS.between(startDate, now);

        return days / 7 + 1;
    }

    private List<WinningLottery> getWinningLotteries(int recentRound) {
        List<WinningLottery> winningLotteries = new ArrayList<>();

        for(int round = 1; round <= recentRound; round++) {
            String winningLotteryInfo = winningLotteryClient.getWinningLotteryInfo(method, round);

            WinningLotteryDto winningLotteryDto = deserialization(winningLotteryInfo);

            WinningLottery winningLottery = makeWinningLottery(winningLotteryDto);

            winningLotteries.add(winningLottery);
        }

        return winningLotteries;
    }

    private WinningLotteryDto deserialization(String winningLotteryInfo) {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            return objectMapper.readValue(winningLotteryInfo, WinningLotteryDto.class);
        } catch (JsonProcessingException e) {
            throw BadRoundException.EXCEPTION;
        }
    }

    private WinningLottery makeWinningLottery(WinningLotteryDto winningLotteryDto) {
        return WinningLottery.builder()
                .round(winningLotteryDto.getDrwNo())
                .winningDate(convertStringToLocalDate(winningLotteryDto.getDrwNoDate()))
                .firstNum(winningLotteryDto.getDrwtNo1())
                .secondNum(winningLotteryDto.getDrwtNo2())
                .thirdNum(winningLotteryDto.getDrwtNo3())
                .fourthNum(winningLotteryDto.getDrwtNo4())
                .fifthNum(winningLotteryDto.getDrwtNo5())
                .sixthNum(winningLotteryDto.getDrwtNo6())
                .bonusNum(winningLotteryDto.getBnusNo())
                .build();
    }

    private LocalDate convertStringToLocalDate(String winningDate) {
        return LocalDate.parse(winningDate);
    }

    private void saveWinningLotteriesInDB(List<WinningLottery> winningLotteries) {
        winningLotteryJdbcRepository.batchInsertWinningLottery(winningLotteries);
    }
}
