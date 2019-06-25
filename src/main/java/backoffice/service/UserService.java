package backoffice.service;

import backoffice.model.User;
import backoffice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    @Override
    public List<User> findAll() {
        return (List<User>) repository.findAll();
    }

    @Override
    public User findUserById(long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public User updateUser(User user){
        return repository.save(user);
    }
}
