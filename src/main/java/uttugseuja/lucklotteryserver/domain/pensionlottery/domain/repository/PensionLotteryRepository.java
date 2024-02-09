package uttugseuja.lucklotteryserver.domain.pensionlottery.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uttugseuja.lucklotteryserver.domain.pensionlottery.domain.PensionLottery;
import uttugseuja.lucklotteryserver.domain.user.domain.User;
import java.util.List;

public interface PensionLotteryRepository extends JpaRepository<PensionLottery, Long> {

    Slice<PensionLottery> findByUserId(Long userId, Pageable pageable);

    @Query("select p from PensionLottery p where p.user = :user and p.rank is null and p.pensionRound <= :pensionRound")
    List<PensionLottery> drawnPensionLottery(User user, Integer pensionRound);

}
