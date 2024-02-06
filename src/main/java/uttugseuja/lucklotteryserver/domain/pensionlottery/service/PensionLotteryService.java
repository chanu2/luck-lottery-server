package uttugseuja.lucklotteryserver.domain.pensionlottery.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.security.SecurityUtil;
import org.hibernate.annotations.Check;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.domain.WinningPensionLottery;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.domain.repository.WinningPensionLotteryRepository;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.dto.response.WinningPensionLotteryBonusNumbersResponse;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.dto.response.WinningPensionLotteryNumbersResponse;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.service.WinningPensionLotteryService;
import uttugseuja.lucklotteryserver.domain.lottery.domain.Lottery;
import uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.response.LotteryNumbersResponse;
import uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.response.LotteryResponse;
import uttugseuja.lucklotteryserver.domain.lottery.presentation.dto.response.WinningLotteryNumbersResponse;
import uttugseuja.lucklotteryserver.domain.pensionlottery.domain.PensionLottery;
import uttugseuja.lucklotteryserver.domain.pensionlottery.domain.repository.PensionLotteryRepository;
import uttugseuja.lucklotteryserver.domain.pensionlottery.exception.OverRoundException;
import uttugseuja.lucklotteryserver.domain.pensionlottery.presentation.dto.request.CreatePensionLotteryRequest;
import uttugseuja.lucklotteryserver.domain.pensionlottery.presentation.dto.response.PensionLotteryNumbersResponse;
import uttugseuja.lucklotteryserver.domain.pensionlottery.presentation.dto.response.PensionLotteryResponse;
import uttugseuja.lucklotteryserver.domain.pensionlottery.presentation.dto.response.RandomPensionLotteryResponse;
import uttugseuja.lucklotteryserver.domain.user.domain.User;
import uttugseuja.lucklotteryserver.global.api.dto.WinningLotteryDto;
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

    private List<Boolean> checkCorrectNumbers(PensionLottery pensionLottery, WinningPensionLottery winningPensionLottery){
        return checkNumbers(pensionLottery, winningPensionLottery.getWinningNumbers(), 0);
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

}
