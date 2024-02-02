package uttugseuja.lucklotteryserver.domain.WinningPensionlottery.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.domain.WinningPensionLottery;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
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


        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                WinningPensionLottery pensionLottery = pensionLotteryList.get(i);
                Timestamp timestamp = Timestamp.valueOf(pensionLottery.getLotteryDrawTime());
                ps.setInt(1,pensionLottery.getRound());
                ps.setTimestamp(2,timestamp);
                ps.setInt(3,pensionLottery.getLotteryGroup());
                ps.setInt(4,pensionLottery.getWinningFirstNum());
                ps.setInt(5,pensionLottery.getWinningSecondNum());
                ps.setInt(6,pensionLottery.getWinningThirdNum());
                ps.setInt(7,pensionLottery.getWinningFourthNum());
                ps.setInt(8,pensionLottery.getWinningFifthNum());
                ps.setInt(9,pensionLottery.getWinningSixthNum());
                ps.setInt(10,pensionLottery.getBonusFirstNum());
                ps.setInt(11,pensionLottery.getBonusSecondNum());
                ps.setInt(12,pensionLottery.getBonusThirdNum());
                ps.setInt(13,pensionLottery.getBonusFourthNum());
                ps.setInt(14,pensionLottery.getBonusFifthNum());
                ps.setInt(15,pensionLottery.getBonusSixthNum());

            }

            @Override
            public int getBatchSize() {
                return pensionLotteryList.size();
            }
        });
    }

}