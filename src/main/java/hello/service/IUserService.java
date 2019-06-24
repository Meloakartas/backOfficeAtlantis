package hello.service;

import hello.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<User> findAll();

    User findUserById(long id);

    User updateUser(User user);
}
