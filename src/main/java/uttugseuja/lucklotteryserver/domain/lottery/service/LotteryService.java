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
import uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.response.LotteryNumbersResponse;
import uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.response.RandomLotteryResponse;
import uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.response.WinningLotteryNumbersResponse;
import uttugseuja.lucklotteryserver.domain.user.domain.User;
import uttugseuja.lucklotteryserver.domain.winning_lottery.service.WinningLotteryUtils;
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
    private final UserUtils userUtils;
    private final WinningLotteryUtils winningLotteryUtils;
    private static final String method = "getLottoNumber";

    public RandomLotteryResponse createRandomLottery() {
        List<Integer> randomNumbers = createRandomNumbers();

        int recentRound = getRecentRound();

        LocalDate winningDate = getRecentWinningDate(recentRound);

        return new RandomLotteryResponse(randomNumbers, recentRound + 1, winningDate.plusDays(7));
    }

    public void saveLottery(CreateLotteryRequest createLotteryRequest) {
        int recentRound = winningLotteryUtils.getRecentRound();
        LocalDate winningDate = winningLotteryUtils.getWinningDate(recentRound);

        User user = userUtils.getUserFromSecurityContext();

        Lottery lottery = makeLottery(user, recentRound + 1,
                winningDate.plusDays(7), createLotteryRequest);

        lotteryRepository.save(lottery);
    }

    public List<LotteryResponse> getLotteriesByUser() {
        User user = userUtils.getUserFromSecurityContext();
        List<Lottery> lotteries = lotteryRepository.findByUser(user);
        List<LotteryResponse> lotteryResponseList = new ArrayList<>();

        for(Lottery lottery : lotteries) {
            LotteryResponse lotteryResponse = processLottery(lottery);
            lotteryResponseList.add(lotteryResponse);
        }

        return lotteryResponseList;
    }

    private LotteryResponse processLottery(Lottery lottery) {
        Integer lotteryRound = lottery.getRound();
        int recentRound = getRecentRound();

        if(lotteryRound <= recentRound) {
            WinningLotteryDto winningLottery = getWinningLottery(lotteryRound);
            List<Integer> lotteryNumbers = getLotteryNumbers(lottery);
            List<Integer> winningLotteryNumbers = getWinningLotteryNumbers(winningLottery);
            List<Integer> correctNumbers = getCorrectNumbers(lotteryNumbers, winningLotteryNumbers);

            if(lottery.getRank() == null) {
                boolean hasBonusNumber = checkBonusNumber(correctNumbers, winningLottery.getBnusNo());
                Rank rank = calLotteryRank(correctNumbers, hasBonusNumber);
                lottery.updateRank(rank);
            }

            return getLotteryResponse(lottery, winningLottery, correctNumbers);
        } else {
            return getLotteryResponse(lottery, null, null);
        }
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

    private Lottery makeLottery(User user, int recentRound, LocalDate winningDate,
                                CreateLotteryRequest createLotteryRequest) {
        return Lottery.builder()
                .user(user)
                .round(recentRound)
                .winningDate(winningDate)
                .firstNum(createLotteryRequest.getFirstNum())
                .secondNum(createLotteryRequest.getSecondNum())
                .thirdNum(createLotteryRequest.getThirdNum())
                .fourthNum(createLotteryRequest.getFourthNum())
                .fifthNum(createLotteryRequest.getFifthNum())
                .sixthNum(createLotteryRequest.getSixthNum())
                .build();
    }

    private LotteryResponse getLotteryResponse(Lottery lottery,
                                               WinningLotteryDto winningLotteryDto,
                                               List<Integer> correctNumbers) {
        LotteryNumbersResponse lotteryNumbersResponse = new LotteryNumbersResponse(lottery.getLotteryBaseInfoVo());
        WinningLotteryNumbersResponse winningLotteryNumbersResponse;
        if(winningLotteryDto == null) {
            winningLotteryNumbersResponse = null;
            correctNumbers = null;
        } else {
            winningLotteryNumbersResponse = new WinningLotteryNumbersResponse(winningLotteryDto);
        }
        return new LotteryResponse(lotteryNumbersResponse, winningLotteryNumbersResponse,
                correctNumbers, lottery.getLotteryBaseInfoVo());
    }

    private Rank calLotteryRank(List<Integer> correctNumbers, boolean hasBonusNumber) {
        int correctSize = correctNumbers.size();
        if(correctSize == 6) {
            if(hasBonusNumber) {
                return Rank.SECOND;
            } else {
                return Rank.FIRST;
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

    private boolean checkBonusNumber(List<Integer> correctNumbers, Integer bonusNumber) {
        if(correctNumbers.contains(bonusNumber)) {
            return true;
        }
        return false;
    }

}
