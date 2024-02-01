package uttugseuja.lucklotteryserver.domain.WinningPensionlottery.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.domain.WinningPensionLottery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class WinningPensionLotteryJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public void batchInsertWinningPensionLottery(List<WinningPensionLottery> pensionLotteryList){

        String sql = "INSERT INTO winning_pension_lottery"
                +  "(round,lottery_draw_time,lottery_group," +
                "winning_first_num,winning_second_num,winning_third_num,winning_fourth_num,winning_fifth_num,winning_sixth_num," +
                "bonus_first_num,bonus_second_num,bonus_third_num,bonus_fourth_num,bonus_fifth_num,bonus_sixth_num) VALUE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    }

}