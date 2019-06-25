package backoffice.service;

import backoffice.model.User;
import backoffice.repository.UserRepository;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserService implements IUserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> findAll() {
        return (List<User>) repository.findAll();
    }

    @Override
    public User findUserById(long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public User findUserByADID(String UserADId) {
        return repository.findByUserADid(UserADId);
    }

    public User saveOrUpdateUser(User user)
    {
        return repository.save(user);
    }
}
