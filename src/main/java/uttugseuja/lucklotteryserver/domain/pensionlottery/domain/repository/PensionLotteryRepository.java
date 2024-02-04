package uttugseuja.lucklotteryserver.domain.pensionlottery.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uttugseuja.lucklotteryserver.domain.lottery.domain.Lottery;
import uttugseuja.lucklotteryserver.domain.pensionlottery.domain.PensionLottery;
import uttugseuja.lucklotteryserver.domain.user.domain.User;

import java.util.List;

public interface PensionLotteryRepository extends JpaRepository<PensionLottery, Long> {

}
