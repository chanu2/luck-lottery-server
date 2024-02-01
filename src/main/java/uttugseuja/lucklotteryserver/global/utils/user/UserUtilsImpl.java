package uttugseuja.lucklotteryserver.global.utils.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uttugseuja.lucklotteryserver.domain.user.domain.User;
import uttugseuja.lucklotteryserver.domain.user.domain.repository.UserRepository;
import uttugseuja.lucklotteryserver.global.exception.UserNotFoundException;
import uttugseuja.lucklotteryserver.global.utils.security.SecurityUtils;


@RequiredArgsConstructor
@Service
public class UserUtilsImpl implements UserUtils{

    private final UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->UserNotFoundException.EXCEPTION);
    }

    @Override
    public User getUserFromSecurityContext() {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        return getUserById(currentUserId);
    }
}
