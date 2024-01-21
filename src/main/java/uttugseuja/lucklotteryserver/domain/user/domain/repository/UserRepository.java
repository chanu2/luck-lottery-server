package uttugseuja.lucklotteryserver.domain.user.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uttugseuja.lucklotteryserver.domain.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
