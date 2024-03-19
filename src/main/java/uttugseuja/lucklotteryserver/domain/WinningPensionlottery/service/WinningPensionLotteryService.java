package uttugseuja.lucklotteryserver.domain.WinningPensionlottery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.dto.response.WinningPensionLotteryResponse;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.exception.CrawlingException;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.exception.DataNotFoundException;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.exception.PageAccessException;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.exception.WinningPensionLotteryNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
@Slf4j
public class WinningPensionLotteryService implements WinningPensionLotteryUtils{

    private final WinningPensionLotteryJdbcRepository winningPensionLotteryJdbcRepository;
    private final WinningPensionLotteryRepository winningPensionLotteryRepository;
    private final static String PENSION_LOTTERY_URL = "https://m.dhlottery.co.kr/gameResult.do?method=win720&Round=";
    private final static String  DRAW_TIME_CSS_QUERY = "div.wrap_select option[selected]";
    private final static String  PRIZE_CSS_QUERY = "div.prize";
    private final static String  GROUP_CSS_QUERY = "h4 strong";
    private final static String  WiN_NUM_CSS_QUERY = "li";

    public WinningPensionLotteryResponse recentWinningPensionLottery(){
        WinningPensionLottery recentWinningPensionLottery = getRecentWinningPensionLottery();
        return new WinningPensionLotteryResponse(recentWinningPensionLottery.getWinningPensionLotteryBaseInfoVo());
    }

    @Override
    public WinningPensionLottery getRecentWinningPensionLottery() {
        return winningPensionLotteryRepository
                .findFirstByOrderByRoundDesc()
                .orElseThrow(()-> WinningPensionLotteryNotFoundException.EXCEPTION);
    }

    @Override
    public WinningPensionLottery getRecentWinningPensionLotteryByRound(Integer round) {
        return winningPensionLotteryRepository
                .findByRound(round)
                .orElseThrow(()-> WinningPensionLotteryNotFoundException.EXCEPTION);
    }

    public WinningPensionLottery createWinningPensionLottery(String round)  {

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

    public WinningPensionLotteryCrawlingDto crawlingWinningPensionLottery(String round) {
        try {
            Document document = getDocumentForRound(round);
            LotteryDrawDayDto lottoDayAndRound = extractLottoDayAndRound(document);
            List<Integer> winningNumber = extractPensionWinningNumbers(document);

            return new WinningPensionLotteryCrawlingDto(
                    lottoDayAndRound.getRound(),
                    lottoDayAndRound.getLotteryDrawTime(),
                    winningNumber);

        } catch (HttpStatusException e) {
            throw PageAccessException.EXCEPTION;

        } catch (IOException e) {
            throw CrawlingException.EXCEPTION;

        } catch (NullPointerException | NumberFormatException e) {
            throw DataNotFoundException.EXCEPTION;
        }
    }

    private Document getDocumentForRound(String round) throws IOException {
        return Jsoup.connect(PENSION_LOTTERY_URL + round).get();
    }

    private LotteryDrawDayDto extractLottoDayAndRound(Document document) {
        Element selectedOption = document.select(DRAW_TIME_CSS_QUERY).first();
        log.info("날짜와 회차 가져오기 ={}",selectedOption);
        String selectedText = selectedOption.text();
        return getLottoDayAndRound(selectedText);
    }

    private List<Integer> extractPensionWinningNumbers(Document document) {
        List<Integer> winningNumber = new ArrayList<>();
        Elements prizes = document.select(PRIZE_CSS_QUERY);
        log.info("조 와 당첨 번호 및 보너스 점수 가져오기={}",prizes);
        for (Element prize : prizes) {
            extractNumbersFromPrize(prize, winningNumber);
        }
        return winningNumber;
    }

    private void extractNumbersFromPrize(Element prize, List<Integer> winningNumber) {
        Elements lotteryGroup = prize.select(GROUP_CSS_QUERY);

        if (!lotteryGroup.isEmpty()) {
            String group = lotteryGroup.first().text();
            winningNumber.add(Integer.parseInt(group));
        }

        Elements winNumbers = prize.select(WiN_NUM_CSS_QUERY);
        for (Element number : winNumbers) {
            Integer num = Integer.parseInt(number.text());
            winningNumber.add(num);
        }
    }


    public void saveWinningPensionLottery(Integer starRound, Integer endRound) {

        List<WinningPensionLottery> winningPensionLotteryList= new ArrayList<>();

        for(int i = starRound; i<=endRound; i++){
            WinningPensionLottery extracted = createWinningPensionLottery(String.valueOf(i));
            winningPensionLotteryList.add(extracted);

        }
        winningPensionLotteryJdbcRepository.batchInsertWinningPensionLottery(winningPensionLotteryList);

    }

    private LotteryDrawDayDto getLottoDayAndRound(String dayAndRound){

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(dayAndRound);
        List<Integer> parsedData = new ArrayList<>();

        while (matcher.find()) {
            parsedData.add(Integer.parseInt(matcher.group()));
        }

        LocalDate drawDay = LocalDate.of(parsedData.get(1), parsedData.get(2), parsedData.get(3));
        Integer drawRound = parsedData.get(0);
        return new LotteryDrawDayDto(drawRound,drawDay);
    }

}
