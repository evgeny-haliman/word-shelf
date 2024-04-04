package word.shelf.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.crypto.password.PasswordEncoder;
import word.shelf.dao.PersonRepository;
import word.shelf.dao.ShelfRepository;
import word.shelf.services.PersonDetailsService;
import word.shelf.services.ShelfService;
import word.shelf.services.TokenService;

@Configuration
public class UserConfig {
    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final TokenService tokenService;
    private final ObjectMapper mapper;

    @Autowired
    public UserConfig(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder, SimpleJdbcInsert simpleJdbcInsert, TokenService tokenService, ObjectMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
        this.simpleJdbcInsert = simpleJdbcInsert;
        this.tokenService = tokenService;
        this.mapper = mapper;
    }

    @Bean
    public PersonRepository personDao() {
        return new PersonRepository(jdbcTemplate, passwordEncoder, simpleJdbcInsert);
    }

    @Bean
    public ShelfRepository shelfRepository() {
        return new ShelfRepository(jdbcTemplate, mapper);
    }

    @Bean
    ShelfService shelfService() {
        return new ShelfService(shelfRepository(), tokenService);
    }

    @Bean
    public PersonDetailsService personDetailsService() {
        return new PersonDetailsService(personDao());
    }
}
