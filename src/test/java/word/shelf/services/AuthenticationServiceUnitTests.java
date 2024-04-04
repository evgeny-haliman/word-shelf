package word.shelf.services;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.containers.PostgreSQLContainer;
import word.shelf.dao.PersonRepository;
import word.shelf.models.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class AuthenticationServiceUnitTests {
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14")
            .withDatabaseName("test")
            .withUsername("test1")
            .withPassword("1234")
            .withExposedPorts(5432);

    @Autowired
    private AuthenticationService service;
    @Autowired
    private PersonRepository personRepository;

    @BeforeAll
    static void startDocker() {
        postgres.start();
    }


    @Test
    public void checkRegisterUser() {
        Person person = new Person();
        person.setName("999");
        person.setPassword("F12");

        service.registerUser(person);

        Person dbPerson = personRepository.findByName(person.getName()).get();

        assertEquals("999", dbPerson.getName());
    }

    @AfterAll
    static void stopDocker() {
        postgres.stop();
    }
}
