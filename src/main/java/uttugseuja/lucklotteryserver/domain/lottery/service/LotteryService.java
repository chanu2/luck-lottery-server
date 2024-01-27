package uttugseuja.lucklotteryserver.domain.lottery.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uttugseuja.lucklotteryserver.domain.lottery.domain.Lottery;
import uttugseuja.lucklotteryserver.domain.lottery.domain.repository.LotteryRepository;
import uttugseuja.lucklotteryserver.domain.lottery.exception.BadRoundException;
import uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.request.CreateLotteryRequest;
import uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.response.LotteryResponse;
import uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.response.RandomLotteryResponse;
import uttugseuja.lucklotteryserver.domain.user.domain.User;
import uttugseuja.lucklotteryserver.global.api.client.WinningLotteryClient;
import uttugseuja.lucklotteryserver.global.api.dto.WinningLotteryDto;
import uttugseuja.lucklotteryserver.global.common.Rank;
import uttugseuja.lucklotteryserver.global.utils.user.UserUtils;

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
    private final UserUtils userUtils;

    public RandomLotteryResponse createRandomLottery() {
        List<Integer> randomNumbers = createRandomNumbers();

        int recentRound = getRecentRound();

        LocalDate winningDate = getRecentWinningDate(recentRound);

        return new RandomLotteryResponse(randomNumbers, recentRound + 1, winningDate.plusDays(7));
    }

    public void saveLottery(CreateLotteryRequest createLotteryRequest) {
        User user = userUtils.getUserFromSecurityContext();

        Lottery lottery = makeLottery(createLotteryRequest, user);

        lotteryRepository.save(lottery);
    }

    private WinningLotteryDto getWinningLottery(Integer round) {
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

    private Lottery makeLottery(CreateLotteryRequest createLotteryRequest, User user) {
        return Lottery.builder()
                .user(user)
                .round(createLotteryRequest.getRound())
                .winningDate(createLotteryRequest.getWinningDate())
                .firstNum(createLotteryRequest.getFirstNum())
                .secondNum(createLotteryRequest.getSecondNum())
                .thirdNum(createLotteryRequest.getThirdNum())
                .fourthNum(createLotteryRequest.getFourthNum())
                .fifthNum(createLotteryRequest.getFifthNum())
                .sixthNum(createLotteryRequest.getSixthNum())
                .build();
    }

    private LotteryResponse getLotteryResponse(Lottery lottery) {
        return new LotteryResponse(lottery.getLotteryBaseInfoVo());
    }

    private Boolean checkBeforeRecentRound(Lottery lottery) {
        int recentRound = getRecentRound();

        if(recentRound >= lottery.getRound()) {
            return true;
        }
        return false;
    }

    private Rank calLotteryRank(Lottery lottery) {
        List<Integer> correctNumbers = getCorrectNumbers(lottery);

        int correctSize = correctNumbers.size();
        if(correctSize == 6) {
            Integer bonusNumber = getBonusNumber(lottery);

            if(correctNumbers.contains(bonusNumber)) {
                return Rank.SECOND;
            } else {
                return Rank.FIFTH;
            }
        } else if(correctSize == 5) {
            return Rank.THIRD;
        } else if(correctSize == 4) {
            return Rank.FOURTH;
        } else if(correctSize == 3) {
            return Rank.FIFTH;
        }
        return Rank.NONE;
    }

    private List<Integer> getCorrectNumbers(List<Integer> lotteryNumbers, List<Integer> winningLotteryNumbers) {
        List<Integer> correctNumbers = new ArrayList<>();

        for(Integer number : lotteryNumbers) {
            if(winningLotteryNumbers.contains(number)) {
                correctNumbers.add(number);
            }
        }

        return correctNumbers;
    }

    private List<Integer> getLotteryNumbers(Lottery lottery) {
        return new ArrayList<>(){{
            add(lottery.getFirstNum());
            add(lottery.getSecondNum());
            add(lottery.getThirdNum());
            add(lottery.getFourthNum());
            add(lottery.getFifthNum());
            add(lottery.getSixthNum());
        }};
    }

    private List<Integer> getWinningLotteryNumbers(WinningLotteryDto winningLottery) {
        return new ArrayList<>(){{
            add(winningLottery.getDrwtNo1());
            add(winningLottery.getDrwtNo2());
            add(winningLottery.getDrwtNo3());
            add(winningLottery.getDrwtNo4());
            add(winningLottery.getDrwtNo5());
            add(winningLottery.getDrwtNo6());
            add(winningLottery.getBnusNo());
        }};
    }

    private Integer getBonusNumber(Lottery lottery) {
        return getWinningLottery(lottery.getRound()).getBnusNo();
    }
}
