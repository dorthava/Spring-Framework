package school21.spring.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

import java.util.Optional;
import java.util.UUID;

@Component
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;

    @Autowired
    public UsersServiceImpl(@Qualifier("usersRepositoryJdbcTemplate") UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public String signUp(String email) {
        Optional<User> user = usersRepository.findByEmail(email);

        if(user.isPresent()) {
            throw new RuntimeException("The user is already logged in!");
        }

        String randomPassword = UUID.randomUUID().toString();
        usersRepository.save(new User(null, email, randomPassword));
        return randomPassword;
    }
}
