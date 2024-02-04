package uttugseuja.lucklotteryserver.domain.pensionlottery.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.domain.WinningPensionLottery;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.service.WinningPensionLotteryService;
import uttugseuja.lucklotteryserver.domain.pensionlottery.domain.repository.PensionLotteryRepository;
import uttugseuja.lucklotteryserver.domain.pensionlottery.presentation.dto.response.RandomPensionLotteryResponse;
import uttugseuja.lucklotteryserver.global.utils.user.UserUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class PensionLotteryService {

    private final WinningPensionLotteryService winningPensionLotteryService;

    public RandomPensionLotteryResponse createRandomPensionLottery() {
        List<Integer> numbers = createRandomPensionNumbers();
        WinningPensionLottery winningPensionLottery = winningPensionLotteryService.getRecentWinningPensionLottery();
        Integer randomRound = winningPensionLottery.getRound() + 1;
        return new RandomPensionLotteryResponse(numbers,randomRound);
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

}
