package word.shelf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import word.shelf.models.Book;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShelfDTO {
    private List<Book> json;
}
