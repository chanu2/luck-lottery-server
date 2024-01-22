package uttugseuja.lucklotteryserver.domain.lottery.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uttugseuja.lucklotteryserver.domain.lottery.domain.Lottery;
import uttugseuja.lucklotteryserver.domain.lottery.domain.repository.LotteryRepository;
import uttugseuja.lucklotteryserver.domain.lottery.exception.BadRoundException;
import uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.response.LotteryResponse;
import uttugseuja.lucklotteryserver.global.api.client.WinningLotteryClient;
import uttugseuja.lucklotteryserver.global.api.dto.WinningLotteryDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class LotteryService {

    private final WinningLotteryClient winningLotteryClient;
    private final LotteryRepository lotteryRepository;
    private static final String method = "getLottoNumber";

    public LotteryResponse createRandomLottery() {
        List<Integer> randomNumbers = createRandomNumbers();

        int recentRound = getRecentRound();

        LocalDate winningDate = getRecentWinningDate(recentRound);

        Lottery lottery = makeLottery(randomNumbers, recentRound + 1, winningDate.plusDays(7));

        lotteryRepository.save(lottery);

        return getLotteryResponse(lottery);
    }

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

    private List<Integer> createRandomNumbers() {
        List<Integer> numbers = new ArrayList<>();
        Random random = new Random();

        for(int i = 0; i < 6; i++) {
            int randomNum = random.nextInt(45) + 1;

            while(numbers.contains(randomNum)) {
                randomNum = random.nextInt(45) + 1;
            }
            numbers.add(randomNum);
        }
        return numbers;
    }

    private int getRecentRound() {
        LocalDateTime startDate = LocalDateTime.of(2002, 12, 7, 20, 0);
        LocalDateTime now = LocalDateTime.now();

        int days = (int) ChronoUnit.DAYS.between(startDate, now);

        return days / 7 + 1;
    }

    private LocalDate getRecentWinningDate(int recentRound) {
        WinningLotteryDto winningLottery = getWinningLottery(recentRound);
        return convertStringToLocalDate(winningLottery.getDrwNoDate());
    }

    private LocalDate convertStringToLocalDate(String winningDate) {
        return LocalDate.parse(winningDate);
    }

    private Lottery makeLottery(List<Integer> randomNumbers, int round, LocalDate winningDate) {
        return Lottery.builder()
                .round(round)
                .winningDate(winningDate)
                .firstNum(randomNumbers.get(0))
                .secondNum(randomNumbers.get(1))
                .thirdNum(randomNumbers.get(2))
                .fourthNum(randomNumbers.get(3))
                .fifthNum(randomNumbers.get(4))
                .sixthNum(randomNumbers.get(5))
                .build();
    }

    private LotteryResponse getLotteryResponse(Lottery lottery) {
        return new LotteryResponse(lottery.getLotteryBaseInfoVo());
    }
}
