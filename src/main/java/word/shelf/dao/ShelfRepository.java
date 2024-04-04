package word.shelf.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import word.shelf.models.Book;
import word.shelf.models.Shelf;

import java.util.List;
import java.util.Optional;

public class ShelfRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper mapper;

    public ShelfRepository(JdbcTemplate jdbcTemplate, ObjectMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    public void save(Shelf shelf) throws JsonProcessingException {
        String sql = "INSERT INTO shelf (id, json) VALUES (?, ?::jsonb)";

        String json = mapper.writeValueAsString(shelf.getJson());
        jdbcTemplate.update(sql, shelf.getId(), json);
    }

    public void update(Shelf shelf) throws JsonProcessingException {
        String sql = "UPDATE shelf SET json = ?::jsonb WHERE id = ?";

        String json = mapper.writeValueAsString(shelf.getJson());
        jdbcTemplate.update(sql, json, shelf.getId());
    }

    public Optional<Shelf> findById(long id) {
        var sql = "SELECT json FROM shelf WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                Shelf shelf = new Shelf();
                String json = rs.getString("json");
                final List<Book> books;
                try {
                    books = mapper.readValue(json, new TypeReference<>() {
                    });
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                shelf.setJson(books);
                return shelf;
            }, id));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }
}
