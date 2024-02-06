package uttugseuja.lucklotteryserver.domain.pensionlottery.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.domain.WinningPensionLottery;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.dto.response.WinningPensionLotteryBonusNumbersResponse;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.dto.response.WinningPensionLotteryNumbersResponse;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.service.WinningPensionLotteryService;
import uttugseuja.lucklotteryserver.domain.pensionlottery.domain.PensionLottery;
import uttugseuja.lucklotteryserver.domain.pensionlottery.domain.repository.PensionLotteryRepository;
import uttugseuja.lucklotteryserver.domain.pensionlottery.presentation.dto.request.CreatePensionLotteryRequest;
import uttugseuja.lucklotteryserver.domain.pensionlottery.presentation.dto.response.PensionLotteryNumbersResponse;
import uttugseuja.lucklotteryserver.domain.pensionlottery.presentation.dto.response.PensionLotteryResponse;
import uttugseuja.lucklotteryserver.domain.pensionlottery.presentation.dto.response.RandomPensionLotteryResponse;
import uttugseuja.lucklotteryserver.domain.user.domain.User;
import uttugseuja.lucklotteryserver.global.common.Rank;
import uttugseuja.lucklotteryserver.global.utils.security.SecurityUtils;
import uttugseuja.lucklotteryserver.global.utils.user.UserUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PensionLotteryService {

    private final UserUtils userUtils;
    private final WinningPensionLotteryService winningPensionLotteryService;
    private final PensionLotteryRepository pensionLotteryRepository;

    @Transactional
    public List<PensionLotteryResponse> getPensionLottery(){

        Long currentUserId = SecurityUtils.getCurrentUserId();
        List<PensionLottery> pensionLotteries = pensionLotteryRepository.findByUserId(currentUserId);

        return pensionLotteries.stream()
                .map(this::updatePensionLottery)
                .collect(Collectors.toList());
    }

    private PensionLotteryResponse updatePensionLottery(PensionLottery pensionLottery) {

        WinningPensionLottery recentWinningPensionLottery = winningPensionLotteryService.getRecentWinningPensionLottery();
        Integer recentRound = recentWinningPensionLottery.getRound();

        if(pensionLottery.getPensionRound() <= recentRound) {
            WinningPensionLottery winningPensionLottery = winningPensionLotteryService.getRecentWinningPensionLotteryByRound(pensionLottery.getPensionRound());
            List<Boolean> checkCorrectNumbers = checkCorrectNumbers(pensionLottery, winningPensionLottery);
            List<Boolean> checkBonusCorrectNumbers = checkBonusCorrectNumbers(pensionLottery, winningPensionLottery);

            if(pensionLottery.getRank() == null) {
                Integer integer = calculateCorrectNumbers(checkCorrectNumbers);
                Rank pensionLotteryResult = getPensionLotteryResult(integer);
                pensionLottery.updateRank(pensionLotteryResult);
                Integer correctBonusCount = calculateCorrectNumbers(checkBonusCorrectNumbers);
                updateCorrectBonus(correctBonusCount,pensionLottery);
            }
            return getPensionLotteryResponse(pensionLottery, winningPensionLottery,checkCorrectNumbers,checkBonusCorrectNumbers);

        }
        return getPensionLotteryEmptyResponse(pensionLottery);
    }

    private PensionLotteryResponse getPensionLotteryResponse(PensionLottery pensionLottery,
                                                             WinningPensionLottery winningPensionLottery,
                                                             List<Boolean> correctNumbers,
                                                             List<Boolean> bonusCorrectNumbers) {

        PensionLotteryNumbersResponse pensionNumbersResponse = new PensionLotteryNumbersResponse(pensionLottery.getPensionLotteryBaseInfoVo());
        WinningPensionLotteryNumbersResponse winningPensionNumbersResponse = new WinningPensionLotteryNumbersResponse(winningPensionLottery.getWinningPensionLotteryBaseInfoVo());
        WinningPensionLotteryBonusNumbersResponse winningPensionLotteryBonusNumbersResponse = new WinningPensionLotteryBonusNumbersResponse(winningPensionLottery.getWinningPensionLotteryBaseInfoVo());

        return new PensionLotteryResponse(
                pensionNumbersResponse,
                winningPensionNumbersResponse,
                winningPensionLotteryBonusNumbersResponse,
                pensionLottery.getPensionLotteryBaseInfoVo(),
                correctNumbers,
                bonusCorrectNumbers
        );
    }

    private PensionLotteryResponse getPensionLotteryEmptyResponse(PensionLottery pensionLottery) {

        PensionLotteryNumbersResponse pensionNumbersResponse = new PensionLotteryNumbersResponse(pensionLottery.getPensionLotteryBaseInfoVo());

        return new PensionLotteryResponse(
                pensionNumbersResponse,
                null,
                null,
                pensionLottery.getPensionLotteryBaseInfoVo(),
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
        WinningPensionLottery winningPensionLottery = winningPensionLotteryService.getRecentWinningPensionLottery();
        Integer randomRound = winningPensionLottery.getRound() + 1;

        if(LocalDateTime.now().isAfter(winningPensionLottery.getLotteryDrawTime().plusDays(7))){
            randomRound+=1;
        }

        return new RandomPensionLotteryResponse(numbers,randomRound);
    }

    public void savePensionLottery(CreatePensionLotteryRequest createPensionLotteryRequest) {
        User user = userUtils.getUserFromSecurityContext();
        WinningPensionLottery winningPensionLottery = winningPensionLotteryService.getRecentWinningPensionLottery();

//        if(createPensionLotteryRequest.getPensionRound() > winningPensionLottery.getRound()+1){
//            throw OverRoundException.EXCEPTION;
//        }

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
                .pensionRound(createPensionLotteryRequest.getPensionRound())
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
