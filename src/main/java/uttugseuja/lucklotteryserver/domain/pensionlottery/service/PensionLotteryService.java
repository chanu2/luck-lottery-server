package uttugseuja.lucklotteryserver.domain.pensionlottery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.domain.WinningPensionLottery;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.dto.response.WinningPensionLotteryBonusNumbersResponse;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.dto.response.WinningPensionLotteryNumbersResponse;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.service.WinningPensionLotteryUtils;
import uttugseuja.lucklotteryserver.domain.pensionlottery.domain.PensionLottery;
import uttugseuja.lucklotteryserver.domain.pensionlottery.domain.repository.PensionLotteryRepository;
import uttugseuja.lucklotteryserver.domain.pensionlottery.presentation.dto.request.CreatePensionLotteryRequest;
import uttugseuja.lucklotteryserver.domain.pensionlottery.presentation.dto.response.PensionLotteryNumbersResponse;
import uttugseuja.lucklotteryserver.domain.pensionlottery.presentation.dto.response.PensionLotteryResponse;
import uttugseuja.lucklotteryserver.domain.pensionlottery.presentation.dto.response.RandomPensionLotteryResponse;
import uttugseuja.lucklotteryserver.domain.user.domain.User;
import uttugseuja.lucklotteryserver.global.common.Rank;
import uttugseuja.lucklotteryserver.global.utils.user.UserUtils;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PensionLotteryService {

    private final UserUtils userUtils;
    private final WinningPensionLotteryUtils winningPensionLotteryUtils;
    private final PensionLotteryRepository pensionLotteryRepository;

    @Transactional
    public Slice<PensionLotteryResponse> getPensionLottery(Pageable pageable){

        User user = userUtils.getUserFromSecurityContext();
        Integer round = winningPensionLotteryUtils.getRecentWinningPensionLottery().getRound();
        List<PensionLottery> drawnPensionLottery = pensionLotteryRepository.drawnPensionLottery(user,round);

        drawnPensionLottery.forEach(this::updatePensionLottery);

        Slice<Integer> pensionRound = pensionLotteryRepository.findRoundByUser(user, pageable);
        List<Integer> rounds = pensionRound.getContent();
        List<PensionLottery> pensionLotteries = pensionLotteryRepository.findPensionLotteryByRounds(user, rounds);

        Map<Integer, List<PensionLottery>> hashMapByRounds = makeHashMapByPensionRound(pensionLotteries);
        return pensionRound.map(lotteryRound -> makePensionResponse(lotteryRound,hashMapByRounds.get(lotteryRound)));
    }

    private PensionLotteryResponse makePensionResponse(Integer round, List<PensionLottery> pensionLotteries) {

        WinningPensionLottery recentWinningPensionLottery = winningPensionLotteryUtils.getRecentWinningPensionLottery();
        Integer pensionRound = recentWinningPensionLottery.getRound();

        if(round <= pensionRound){
            WinningPensionLottery winningPensionLottery = winningPensionLotteryUtils.getRecentWinningPensionLotteryByRound(round);
            List<PensionLotteryNumbersResponse> pensionLotteryNumbersResponses = makePreviousPensionLotteryNumbers(pensionLotteries, winningPensionLottery);

            return getPensionLotteryResponse(round,
                    winningPensionLottery.getLotteryDrawTime(),
                    winningPensionLottery,
                    pensionLotteryNumbersResponses);
        }
        List<PensionLotteryNumbersResponse> pensionLotteryNumbersResponses = makeRecentPensionLotteryNumbers(pensionLotteries);

        return getPensionLotteryEmptyResponse(pensionRound+1,
                recentWinningPensionLottery.getLotteryDrawTime().plusDays(7),
                pensionLotteryNumbersResponses);
    }

    private HashMap<Integer, List<PensionLottery>> makeHashMapByPensionRound(List<PensionLottery> pensionLotteries) {
        return pensionLotteries.stream()
                .collect(Collectors.groupingBy(PensionLottery::getPensionRound, HashMap::new, Collectors.toList()));
    }

    private List<PensionLotteryNumbersResponse> makePreviousPensionLotteryNumbers(List<PensionLottery> pensionLotteries,
                                                                                  WinningPensionLottery winningPensionLottery) {
        return pensionLotteries.stream()
                .map(pensionLottery -> {
                    List<Boolean> correctNumbers = checkCorrectNumbers(pensionLottery, winningPensionLottery);
                    List<Boolean> correctBonusNumbers = checkBonusCorrectNumbers(pensionLottery, winningPensionLottery);
                    return new PensionLotteryNumbersResponse(pensionLottery.getPensionLotteryBaseInfoVo(), correctNumbers,correctBonusNumbers);
                })
                .collect(Collectors.toList());
    }

    private List<PensionLotteryNumbersResponse> makeRecentPensionLotteryNumbers(List<PensionLottery> pensionLotteries) {
        return pensionLotteries.stream()
                .map(pensionLottery -> new PensionLotteryNumbersResponse(pensionLottery.getPensionLotteryBaseInfoVo(), null, null))
                .collect(Collectors.toList());
    }

    private void updatePensionLottery(PensionLottery pensionLottery) {

            WinningPensionLottery winningPensionLottery = winningPensionLotteryUtils.getRecentWinningPensionLotteryByRound(pensionLottery.getPensionRound());
            List<Boolean> checkCorrectNumbers = checkCorrectNumbers(pensionLottery, winningPensionLottery);
            List<Boolean> checkBonusCorrectNumbers = checkBonusCorrectNumbers(pensionLottery, winningPensionLottery);

            Integer correctCount = calculateCorrectNumbers(checkCorrectNumbers);
            Rank pensionLotteryResult = getPensionLotteryResult(correctCount);
            pensionLottery.updateRank(pensionLotteryResult);

            Integer correctBonusCount = calculateCorrectNumbers(checkBonusCorrectNumbers);
            updateCorrectBonus(correctBonusCount,pensionLottery);
    }

    private PensionLotteryResponse getPensionLotteryResponse(Integer round,
                                                             LocalDate winningDate,
                                                             WinningPensionLottery winningPensionLottery,
                                                             List<PensionLotteryNumbersResponse> pensionLotteryNumbersResponseList) {

        WinningPensionLotteryNumbersResponse winningPensionNumbersResponse = new WinningPensionLotteryNumbersResponse(winningPensionLottery.getWinningPensionLotteryBaseInfoVo());
        WinningPensionLotteryBonusNumbersResponse winningPensionLotteryBonusNumbersResponse = new WinningPensionLotteryBonusNumbersResponse(winningPensionLottery.getWinningPensionLotteryBaseInfoVo());

        return new PensionLotteryResponse(
                round,
                winningDate,
                pensionLotteryNumbersResponseList,
                winningPensionNumbersResponse,
                winningPensionLotteryBonusNumbersResponse
        );
    }

    private PensionLotteryResponse getPensionLotteryEmptyResponse(Integer round,
                                                                  LocalDate winningDate,
                                                                  List<PensionLotteryNumbersResponse> pensionLotteryNumbersResponseList) {

        return new PensionLotteryResponse(
                round,
                winningDate,
                pensionLotteryNumbersResponseList,
                null,
                null
        );
    }

    private Rank getPensionLotteryResult(Integer count){

        switch(count) {
            case 0:
                return Rank.NONE;
            case 1:
                return Rank.SEVENTH;
            case 2:
                return Rank.SIXTH;
            case 3:
                return Rank.FIFTH;
            case 4:
                return Rank.FOURTH;
            case 5:
                return Rank.THIRD;
            case 6:
                return Rank.SECOND;
            case 7:
                return Rank.FIRST;
            default:
                throw new RuntimeException("해당 타입 없음.");
        }

    }

    private Integer calculateCorrectNumbers(List<Boolean> checkNumbers){

        int count = 0;

        for(int i= checkNumbers.size()-1; i>-1; i--){

            if (!checkNumbers.get(i)){
                break;
            }
            count++;

        }
        return count;
    }

    private List<Boolean> checkCorrectNumbers(PensionLottery pensionLottery, WinningPensionLottery winningPensionLottery){
        return checkNumbers(pensionLottery, winningPensionLottery.getWinningNumbers(), 0);
    }

    private List<Boolean> checkBonusCorrectNumbers(PensionLottery pensionLottery, WinningPensionLottery winningPensionLottery){
        return checkNumbers(pensionLottery, winningPensionLottery.getWinningBonusNumbers(), 1);
    }

    private List<Boolean> checkNumbers(PensionLottery pensionLottery, List<Integer> winningNumbers, int offset){

        List<Boolean> check = new ArrayList<>();
        List<Integer> numbers = pensionLottery.getNumbers();

        for(int i=0; i<winningNumbers.size(); i++){
            check.add(numbers.get(i+offset).equals(winningNumbers.get(i)));
        }

        return check;
    }

    public RandomPensionLotteryResponse createRandomPensionLottery() {
        List<Integer> numbers = createRandomPensionNumbers();
        WinningPensionLottery winningPensionLottery = winningPensionLotteryUtils.getRecentWinningPensionLottery();
        Integer randomRound = winningPensionLottery.getRound() + 1;
        return new RandomPensionLotteryResponse(numbers,randomRound);
    }

    public void savePensionLottery(CreatePensionLotteryRequest createPensionLotteryRequest) {
        User user = userUtils.getUserFromSecurityContext();
        WinningPensionLottery winningPensionLottery = winningPensionLotteryUtils.getRecentWinningPensionLottery();
        PensionLottery pensionLottery = makePensionLottery(createPensionLotteryRequest, user, winningPensionLottery);
        pensionLotteryRepository.save(pensionLottery);
    }

    private List<Integer> createRandomPensionNumbers() {

        List<Integer> pensionNumbers = new ArrayList<>();
        Random random = new Random();
        Integer group = random.nextInt(5)+1;
        pensionNumbers.add(group);

        for(int i = 0; i < 6; i++) {
            int randomNum = random.nextInt(9);
            pensionNumbers.add(randomNum);
        }

        return pensionNumbers;
    }

    private PensionLottery makePensionLottery(CreatePensionLotteryRequest createPensionLotteryRequest, User user, WinningPensionLottery winningPensionLottery) {
        return PensionLottery.builder()
                .user(user)
                .winningDate(winningPensionLottery.getLotteryDrawTime().plusDays(7))
                .checkWinningBonus(false)
                .pensionRound(winningPensionLottery.getRound()+1)
                .pensionGroup(createPensionLotteryRequest.getPensionGroup())
                .pensionFirstNum(createPensionLotteryRequest.getPensionFirstNum())
                .pensionSecondNum(createPensionLotteryRequest.getPensionSecondNum())
                .pensionThirdNum(createPensionLotteryRequest.getPensionThirdNum())
                .pensionFourthNum(createPensionLotteryRequest.getPensionFourthNum())
                .pensionFifthNum(createPensionLotteryRequest.getPensionFifthNum())
                .pensionSixthNum(createPensionLotteryRequest.getPensionSixthNum())
                .build();
    }

    private void updateCorrectBonus(Integer correctBonusCount,PensionLottery pensionLottery){

        if(correctBonusCount == 6){
            pensionLottery.updateCheckWinningBonus();
        }

    }

}
