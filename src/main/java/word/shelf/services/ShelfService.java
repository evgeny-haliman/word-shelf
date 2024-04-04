package word.shelf.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import word.shelf.dao.ShelfRepository;
import word.shelf.models.Shelf;

public class ShelfService {
    private final ShelfRepository shelfRepository;
    private final TokenService tokenService;

    public ShelfService(ShelfRepository shelfRepository, TokenService tokenService) {
        this.shelfRepository = shelfRepository;
        this.tokenService = tokenService;
    }

    public void saveShelf(Shelf shelf, HttpServletRequest request) {
        String token = tokenService.extractTokenFromHeader(request);
        long id = tokenService.getIdFromToken(token);
        shelf.setId(id);
        try {
            shelfRepository.save(shelf);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateShelf(Shelf shelf, HttpServletRequest request) {
        String token = tokenService.extractTokenFromHeader(request);
        long id = tokenService.getIdFromToken(token);
        shelf.setId(id);
        try {
            shelfRepository.update(shelf);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
