package word.shelf.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {
    @NotEmpty
    private String word;
    @NotEmpty
    private String transcription;
    @NotEmpty
    private String translation;
    @NotEmpty
    private String sentence;
}
