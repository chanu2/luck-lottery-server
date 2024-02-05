package uttugseuja.lucklotteryserver.domain.winning_lottery.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uttugseuja.lucklotteryserver.domain.winning_lottery.domain.WinningLottery;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class WinningLotteryJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public void batchInsertWinningLottery(List<WinningLottery> winningLotteryList) {

        String sql = "INSERT INTO winning_lottery"
                +  "(round, winning_date, first_num, second_num, third_num, fourth_num, fifth_num," +
                "sixth_num, bonus_num) VALUE(?,?,?,?,?,?,?,?,?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                WinningLottery winningLottery = winningLotteryList.get(i);
                ps.setInt(1, winningLottery.getRound());
                ps.setDate(2, Date.valueOf(winningLottery.getWinningDate()));
                ps.setInt(3, winningLottery.getFirstNum());
                ps.setInt(4, winningLottery.getSecondNum());
                ps.setInt(5, winningLottery.getThirdNum());
                ps.setInt(6, winningLottery.getFourthNum());
                ps.setInt(7, winningLottery.getFifthNum());
                ps.setInt(8, winningLottery.getSixthNum());
                ps.setInt(9, winningLottery.getBonusNum());
            }

            @Override
            public int getBatchSize() {
                return winningLotteryList.size();
            }
        });
    }
}
