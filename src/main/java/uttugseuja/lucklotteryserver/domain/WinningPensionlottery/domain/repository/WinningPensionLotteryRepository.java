package uttugseuja.lucklotteryserver.domain.WinningPensionlottery.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uttugseuja.lucklotteryserver.domain.WinningPensionlottery.domain.WinningPensionLottery;
import java.util.Optional;

public interface WinningPensionLotteryRepository extends JpaRepository<WinningPensionLottery, Long> {
    Optional<WinningPensionLottery> findFirstByOrderByRoundDesc();

    Optional<WinningPensionLottery> findByRound(Integer round);
}
