package school21.spring.service.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import school21.spring.service.config.TestApplicationConfig;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestApplicationConfig.class)
public class UsersServiceImplTest {
    @Autowired
    @Qualifier("usersRepositoryJdbc")
    private UsersRepository usersRepository;

    @Autowired
    private UsersService usersService;

    @Test
    public void testUserAlreadySignUp() {
        String email = "pupupu@gmail.com";
        usersService.signUp(email);
        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> usersService.signUp(email));
        assertEquals("The user is already logged in!", thrownException.getMessage());
        for(User user : usersRepository.findAll()) {
            System.out.println(user);
        }
        usersRepository.delete(0L);
    }

    @Test
    public void testSignUp() {
        String email = "pupupu@gmail.com";
        String password = usersService.signUp(email);
        assertEquals(36, password.length());
        User user = usersRepository.findByEmail(email).orElse(null);
        assertNotNull(user);
        assertEquals(email, user.getEmail());
    }
}
