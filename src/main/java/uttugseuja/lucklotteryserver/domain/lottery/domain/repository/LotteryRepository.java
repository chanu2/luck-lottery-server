package uttugseuja.lucklotteryserver.domain.lottery.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uttugseuja.lucklotteryserver.domain.lottery.domain.Lottery;
import uttugseuja.lucklotteryserver.domain.user.domain.User;

import java.util.List;


public interface LotteryRepository extends JpaRepository<Lottery, Long> {
    @Query("select distinct l.round from Lottery l where l.user = :user order by l.round desc")
    Slice<Integer> findRoundByUser(@Param("user") User user, Pageable pageable);

    @Query("SELECT l FROM Lottery l WHERE l.user = :user AND l.rank IS NULL AND l.round <= :round")
    List<Lottery> findLotteriesByUserAndRound(@Param("user") User user, @Param("round") Integer round);

    List<Lottery> findByUser(User user);
}
