package backoffice.repository;

import backoffice.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        User userToCreate = new User(1L, "1", "firstName", "lastName");
        User createdUser = userRepository.save(userToCreate);
        Assert.assertNotNull(createdUser);
    }

    @Test
    public void testFindUserById() {
        User userToCreate = new User(1L, "1", "firstName", "lastName");
        userRepository.save(userToCreate);
        Optional<User> createdUser = userRepository.findById(1L);
        Assert.assertEquals(userToCreate.getId(), createdUser.get().getId());
    }

    @Test
    public void testFindUserByUserADid() {
        User userToCreate = new User(1L, "1", "firstName", "lastName");
        userRepository.save(userToCreate);
        User createdUser = userRepository.findByUserADid("1");
        Assert.assertEquals(userToCreate.getUserADid(), createdUser.getUserADid());
    }

    @Test
    public void testFindAll() {
        User userToCreate = new User(1L, "1", "firstName", "lastName");
        userRepository.save(userToCreate);
        List<User> users = (List<User>) userRepository.findAll();
        Assert.assertNotNull(users);
    }
}