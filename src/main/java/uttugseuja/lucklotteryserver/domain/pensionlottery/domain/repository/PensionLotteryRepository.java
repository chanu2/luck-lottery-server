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

    @Query("select distinct p.pensionRound from PensionLottery p where p.user = :user order by p.pensionRound desc")
    Slice<Integer> findRoundByUser(User user, Pageable pageable);

    @Query("SELECT p FROM PensionLottery p WHERE p.user = :user AND p.pensionRound IN :rounds")
    List<PensionLottery> findPensionLotteryByRounds(@Param("user") User user, @Param("rounds") List<Integer> rounds);

}
