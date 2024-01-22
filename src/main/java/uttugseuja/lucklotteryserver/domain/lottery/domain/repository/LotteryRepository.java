package uttugseuja.lucklotteryserver.domain.lottery.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uttugseuja.lucklotteryserver.domain.lottery.domain.Lottery;

public interface LotteryRepository extends JpaRepository<Lottery, Long> {
}
