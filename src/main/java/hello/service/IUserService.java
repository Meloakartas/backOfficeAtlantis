package hello.service;

import hello.model.User;

import java.util.List;

public interface IUserService {

    List<User> findAll();
}
