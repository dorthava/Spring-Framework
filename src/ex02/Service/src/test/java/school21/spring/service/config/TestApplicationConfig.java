package school21.spring.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.repositories.UsersRepositoryJdbcImpl;
import school21.spring.service.services.UsersService;
import school21.spring.service.services.UsersServiceImpl;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
public class TestApplicationConfig {
    @Bean
    @Qualifier("dataSource")
    public DataSource dataSource() {
        DataSource dataSource =  new EmbeddedDatabaseBuilder().generateUniqueName(true).build();
        String statementWords = "CREATE TABLE \"user\" (identifier BIGINT IDENTITY PRIMARY KEY, email VARCHAR(255), password VARCHAR(255));";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(statementWords);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return dataSource;
    }

    @Bean
    @Qualifier("usersRepositoryJdbc")
    public UsersRepository usersRepositoryJdbc(DataSource dataSource) {
        return new UsersRepositoryJdbcImpl(dataSource);
    }

    @Bean
    @Qualifier("usersRepositoryJdbcTemplate")
    public UsersRepository usersRepositoryJdbcTemplate(DataSource dataSource) {
        return new UsersRepositoryJdbcImpl(dataSource);
    }

    @Bean
    @Qualifier("usersServiceJdbc")
    public UsersService usersService(@Qualifier("usersRepositoryJdbc") UsersRepository usersRepository) {
        return new UsersServiceImpl(usersRepository);
    }
}
