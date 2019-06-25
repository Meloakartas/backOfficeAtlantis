package backoffice.service;

import backoffice.model.User;
import java.util.List;

public interface IUserService {

    List<User> findAll();

    User findUserById(long id);

    User findUserByADID(String UserADId);

    User saveOrUpdateUser(User user);
}
