package uttugseuja.lucklotteryserver.domain.lottery.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uttugseuja.lucklotteryserver.domain.lottery.domain.Lottery;
import uttugseuja.lucklotteryserver.domain.user.domain.User;

import java.util.List;

public interface LotteryRepository extends JpaRepository<Lottery, Long> {
    List<Lottery> findByUser(User user);
}
