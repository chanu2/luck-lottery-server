package uttugseuja.lucklotteryserver.domain.winning_lottery.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uttugseuja.lucklotteryserver.domain.winning_lottery.domain.WinningLottery;

import java.util.Optional;

public interface WinningLotteryRepository extends JpaRepository<WinningLottery, Long> {
    Optional<WinningLottery> findByRound(Integer round);
}
