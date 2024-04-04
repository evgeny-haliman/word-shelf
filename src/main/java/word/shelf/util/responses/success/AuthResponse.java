package word.shelf.util.responses.success;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import word.shelf.models.Book;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {
    private String jwt;
    private List<Book> books;
}
