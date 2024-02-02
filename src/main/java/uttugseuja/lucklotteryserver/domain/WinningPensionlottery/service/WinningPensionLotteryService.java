package uttugseuja.lucklotteryserver.domain.WinningPensionlottery.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.domain.WinningPensionLottery;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.domain.repository.WinningPensionLotteryJdbcRepository;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.domain.repository.WinningPensionLotteryRepository;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.dto.request.LotteryDrawDayDto;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.dto.request.WinningPensionLotteryCrawlingDto;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.exception.CrawlingIOException;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.exception.DataNotFoundException;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.exception.PageAccessException;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.exception.WinningPensionLotteryNotFoundException;
import uttugseuja.lucklotteryserver.global.error.exception.LuckLotteryIoException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
@Slf4j
public class WinningPensionLotteryService {

    private final WinningPensionLotteryJdbcRepository winningPensionLotteryJdbcRepository;
    private final WinningPensionLotteryRepository winningPensionLotteryRepository;
    private final static String PENSION_LOTTERY_URL = "https://m.dhlottery.co.kr/gameResult.do?method=win720&Round=";
    private final static String  DRAW_TIME_CSS_QUERY = "div.wrap_select option[selected]";
    private final static String  PRIZE_CSS_QUERY = "div.prize";
    private final static String  GROUP_CSS_QUERY = "h4 strong";
    private final static String  WiN_NUM_CSS_QUERY = "li";

    public WinningPensionLottery getRecentWinningPensionLottery() {
        return winningPensionLotteryRepository
                .findFirstByOrderByRoundDesc()
                .orElseThrow(()-> WinningPensionLotteryNotFoundException.EXCEPTION);
    }

    private WinningPensionLotteryCrawlingDto crawlingWinningPensionLottery(String round) throws LuckLotteryIoException {

        List<Integer> winningNumber = new ArrayList<>();

        try{
            Connection conn = Jsoup.connect(PENSION_LOTTERY_URL+round);
            Document document = null;
            document = (Document) conn.get();
            Element selectedOption = document.select(DRAW_TIME_CSS_QUERY).first();

            String selectedText = selectedOption.text();
            LotteryDrawDayDto lottoDayAndRound = getLottoDayAndRound(selectedText);

            log.info("lottoDayAndRound.getRound()={}",lottoDayAndRound.getRound());
            log.info("lottoDayAndRound.getLotteryDrawTime()={}",lottoDayAndRound.getLotteryDrawTime());

            Elements prizes = document.select(PRIZE_CSS_QUERY);

            for (Element prize : prizes) {
                Elements lotteryGroup = prize.select(GROUP_CSS_QUERY);

                if(!lotteryGroup.isEmpty()){
                    String group = lotteryGroup.first().text();
                    winningNumber.add(Integer.parseInt(group));
                }
                Elements winNumbers = prize.select(WiN_NUM_CSS_QUERY);

                for (Element number : winNumbers) {
                    Integer num = Integer.parseInt(number.text());
                    winningNumber.add(num);
                }
            }
            return new WinningPensionLotteryCrawlingDto(
                    lottoDayAndRound.getRound(),
                    lottoDayAndRound.getLotteryDrawTime(),
                    winningNumber);

        } catch (HttpStatusException e) {
            throw PageAccessException.EXCEPTION;

        } catch (IOException e) {
            throw CrawlingIOException.EXCEPTION;

        } catch (NullPointerException e) {
            throw DataNotFoundException.EXCEPTION;
        }
    }

    public void saveWinningPensionLottery(Integer starRound, Integer endRound) throws LuckLotteryIoException {

        List<WinningPensionLottery> winningPensionLotteryList= new ArrayList<>();

        for(int i = starRound; i<=endRound; i++){
            WinningPensionLottery extracted = createWinningPensionLottery(String.valueOf(i));
            winningPensionLotteryList.add(extracted);

        }
        winningPensionLotteryJdbcRepository.batchInsertWinningPensionLottery(winningPensionLotteryList);

    }

    public WinningPensionLottery createWinningPensionLottery(String round) throws LuckLotteryIoException {

        WinningPensionLotteryCrawlingDto process = crawlingWinningPensionLottery(round);
        List<Integer> nums = process.getWinningNumbers();
        return WinningPensionLottery.builder()
                .lotteryDrawTime(process.getLotteryDrawTime())
                .round(process.getPensionLotteryRound())
                .lotteryGroup(nums.get(0))
                .winningFirstNum(nums.get(1))
                .winningSecondNum(nums.get(2))
                .winningThirdNum(nums.get(3))
                .winningFourthNum(nums.get(4))
                .winningFifthNum(nums.get(5))
                .winningSixthNum(nums.get(6))
                .bonusFirstNum(nums.get(7))
                .bonusSecondNum(nums.get(8))
                .bonusThirdNum(nums.get(9))
                .bonusFourthNum(nums.get(10))
                .bonusFifthNum(nums.get(11))
                .bonusSixthNum(nums.get(12))
                .build();
    }

    private LotteryDrawDayDto getLottoDayAndRound(String dayAndRound){

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(dayAndRound);

        List<Integer> parsedData = new ArrayList<>();

        while (matcher.find()) {
            parsedData.add(Integer.parseInt(matcher.group()));
        }

        LocalDateTime drawDay = LocalDateTime.of(parsedData.get(1), parsedData.get(2), parsedData.get(3), 17,0);

        Integer drawRound = parsedData.get(0);

        return new LotteryDrawDayDto(drawRound,drawDay);

    }


}
