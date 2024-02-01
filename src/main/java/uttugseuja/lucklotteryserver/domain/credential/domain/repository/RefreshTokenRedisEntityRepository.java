package uttugseuja.lucklotteryserver.domain.credential.domain.repository;


import org.springframework.data.repository.CrudRepository;
import uttugseuja.lucklotteryserver.domain.credential.domain.RefreshTokenRedisEntity;

import java.util.Optional;

public interface RefreshTokenRedisEntityRepository
        extends CrudRepository<RefreshTokenRedisEntity, String> {
    Optional<RefreshTokenRedisEntity> findByRefreshToken(String refreshToken);
}
