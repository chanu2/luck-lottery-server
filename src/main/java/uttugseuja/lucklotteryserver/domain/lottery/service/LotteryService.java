package uttugseuja.lucklotteryserver.domain.lottery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uttugseuja.lucklotteryserver.domain.lottery.domain.Lottery;
import uttugseuja.lucklotteryserver.domain.lottery.domain.repository.LotteryRepository;
import uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.request.CreateLotteryRequest;
import uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.response.OneRoundResponse;
import uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.response.LotteryResponse;
import uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.response.RandomLotteryResponse;
import uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.response.WinningLotteryNumbersResponse;
import uttugseuja.lucklotteryserver.domain.user.domain.User;
import uttugseuja.lucklotteryserver.domain.winning_lottery.domain.WinningLottery;
import uttugseuja.lucklotteryserver.domain.winning_lottery.service.WinningLotteryUtils;
import uttugseuja.lucklotteryserver.global.common.Rank;
import uttugseuja.lucklotteryserver.global.utils.user.UserUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class LotteryService {

    private final LotteryRepository lotteryRepository;
    private final UserUtils userUtils;
    private final WinningLotteryUtils winningLotteryUtils;

    public RandomLotteryResponse createRandomLottery() {
        List<Integer> randomNumbers = createRandomNumbers();

        int recentRound = winningLotteryUtils.getRecentRound();

        LocalDate winningDate = winningLotteryUtils.getWinningLottery(recentRound).getWinningDate();

        return new RandomLotteryResponse(randomNumbers, recentRound + 1, winningDate.plusDays(7));
    }

    @Transactional
    public void saveLottery(CreateLotteryRequest createLotteryRequest) {
        int recentRound = winningLotteryUtils.getRecentRound();
        LocalDate winningDate = winningLotteryUtils.getWinningLottery(recentRound).getWinningDate();

        User user = userUtils.getUserFromSecurityContext();

        Lottery lottery = makeLottery(user, recentRound + 1,
                winningDate.plusDays(7), createLotteryRequest);

        lotteryRepository.save(lottery);
    }

    @Transactional
    public Slice<OneRoundResponse> getLotteriesByUser(Pageable pageable) {
        User user = userUtils.getUserFromSecurityContext();

        int recentRound = winningLotteryUtils.getRecentRound();

        List<Lottery> lotteriesIsRankNull = lotteryRepository.findLotteriesByUserAndRound(user, recentRound);
        updateLotteriesRank(lotteriesIsRankNull);

        Slice<Integer> rounds = lotteryRepository.findRoundByUser(user, pageable);
        List<Lottery> lotteries = lotteryRepository.findByUser(user);
        HashMap<Integer, List<Lottery>> roundMap = makeHashMapByRound(lotteries);

        return rounds.map(lotteryRound -> processLottery(lotteryRound, recentRound, roundMap.get(lotteryRound)));
    }

    private void updateLotteriesRank(List<Lottery> lotteriesIsRankNull) {
        for(Lottery lottery : lotteriesIsRankNull) {
            WinningLottery winningLottery = winningLotteryUtils.getWinningLottery(lottery.getRound());

            List<Integer> correctNumbers = getCorrectNumbers(getLotteryNumbers(lottery),
                    getWinningLotteryNumbers(winningLottery));

            Rank rank = calLotteryRank(correctNumbers, winningLottery.getBonusNum());

            lottery.updateRank(rank);
        }
    }

    private HashMap<Integer, List<Lottery>> makeHashMapByRound(List<Lottery> lotteries) {
        return lotteries.stream()
                .collect(Collectors.groupingBy(Lottery::getRound, HashMap::new, Collectors.toList()));
    }

    private OneRoundResponse processLottery(Integer lotteryRound, Integer recentRound, List<Lottery> lotteries) {
        if(lotteryRound <= recentRound) {
            WinningLottery winningLottery = winningLotteryUtils.getWinningLottery(lotteryRound);

            List<LotteryResponse> lotteryResponses = makeLotteryResponsesNotRecent(lotteries, winningLottery);

            WinningLotteryNumbersResponse winningLotteryNumbersResponse =
                    new WinningLotteryNumbersResponse(winningLottery.getWinningLotteryBaseInfoVo());

            return new OneRoundResponse(lotteryRound, winningLottery.getWinningDate(),
                    lotteryResponses, winningLotteryNumbersResponse);
        } else {
            LocalDate winningDate =
                    winningLotteryUtils.getWinningLottery(recentRound).getWinningDate().plusDays(7);

            List<LotteryResponse> lotteryResponses = makeLotteryResponseRecent(lotteries);
            return new OneRoundResponse(lotteryRound, winningDate, lotteryResponses, null);
        }
    }

    private List<LotteryResponse> makeLotteryResponsesNotRecent(List<Lottery> lotteries,
                                                                WinningLottery winningLottery) {
        return lotteries.stream()
                .map(lottery -> {
                    List<Integer> correctNumbers = getCorrectNumbers(getLotteryNumbers(lottery),
                            getWinningLotteryNumbers(winningLottery));

                    List<Boolean> lotteryResult = getLotteryResult(correctNumbers, getLotteryNumbers(lottery));
                    return new LotteryResponse(lottery.getLotteryBaseInfoVo(), lotteryResult);
                })
                .collect(Collectors.toList());
    }

    private List<LotteryResponse> makeLotteryResponseRecent(List<Lottery> lotteries) {
        return lotteries.stream()
                .map(lottery -> new LotteryResponse(lottery.getLotteryBaseInfoVo(), null))
                .collect(Collectors.toList());
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

    private Rank calLotteryRank(List<Integer> correctNumbers, Integer bonusNumber) {
        int correctSize = correctNumbers.size();

        if(correctSize == 6) {
            if(correctNumbers.contains(bonusNumber)) {
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

    private List<Integer> getWinningLotteryNumbers(WinningLottery winningLottery) {
        return new ArrayList<>(){{
            add(winningLottery.getFirstNum());
            add(winningLottery.getSecondNum());
            add(winningLottery.getThirdNum());
            add(winningLottery.getFourthNum());
            add(winningLottery.getFifthNum());
            add(winningLottery.getSixthNum());
            add(winningLottery.getBonusNum());
        }};
    }

    private List<Boolean> getLotteryResult(List<Integer> lotteryNumbers, List<Integer> correctNumbers) {
        List<Boolean> lotteryResult = new ArrayList<>();

        for(Integer number : lotteryNumbers) {
            if(correctNumbers.contains(number)) {
                lotteryResult.add(true);
            } else {
                lotteryResult.add(false);
            }
        }

        return lotteryResult;
    }
}
