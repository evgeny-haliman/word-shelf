package word.shelf.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.crypto.password.PasswordEncoder;
import word.shelf.models.Person;
import word.shelf.util.exceptions.PersonAlreadyExistException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class PersonRepository {

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public PersonRepository(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder, SimpleJdbcInsert simpleJdbcInsert) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
        this.simpleJdbcInsert = simpleJdbcInsert;
    }

    public List<Person> getAllPersons() {
        String sql = "SELECT * FROM person";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Person.class));
    }

    public Optional<Person> getById(int id) {
        var sql = "SELECT * FROM person WHERE id=?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Person.class), new Object[]{id}).stream().findAny();
    }

    public Optional<Person> findByName(String name) {
        var sql = "SELECT * FROM person WHERE name=?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Person.class), new Object[]{name}).stream().findAny();
    }

    public int save(Person person) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", person.getName());
        parameters.put("password", passwordEncoder.encode(person.getPassword()));
        Number id;

        try {
            id = simpleJdbcInsert.executeAndReturnKey(parameters);
        } catch (DataAccessException e) {
            throw new PersonAlreadyExistException();
        }
        return (int) id;
    }

    public void deleteByName(String name) {
        var sql = "DELETE FROM person WHERE name=?";
        jdbcTemplate.update(sql, name);
    }
}