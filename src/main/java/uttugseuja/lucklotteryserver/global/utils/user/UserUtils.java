package uttugseuja.lucklotteryserver.global.utils.user;

import uttugseuja.lucklotteryserver.domain.user.domain.User;

public interface UserUtils {

    User getUserById(Long id);

    User getUserFromSecurityContext();

}
