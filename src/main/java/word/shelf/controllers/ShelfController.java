package word.shelf.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import word.shelf.dto.ShelfDTO;
import word.shelf.models.Shelf;
import word.shelf.services.ShelfService;

@RestController
@CrossOrigin("*")
public class ShelfController {
    private final ModelMapper modelMapper;
    private final ShelfService shelfService;

    public ShelfController(ModelMapper modelMapper, ShelfService shelfService) {
        this.modelMapper = modelMapper;
        this.shelfService = shelfService;
    }

    @PostMapping("/data")
    public ResponseEntity<?> save(@RequestBody ShelfDTO shelfDTO, HttpServletRequest request) {
        Shelf shelfToAdd = convertToShelf(shelfDTO);

        shelfService.saveShelf(shelfToAdd, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody ShelfDTO shelfDTO, HttpServletRequest request) {
        Shelf shelfToAdd = convertToShelf(shelfDTO);

        shelfService.updateShelf(shelfToAdd, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Shelf convertToShelf(ShelfDTO shelfDTO) {
        return modelMapper.map(shelfDTO, Shelf.class);
    }
}
