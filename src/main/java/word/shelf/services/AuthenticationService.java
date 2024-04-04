package word.shelf.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import word.shelf.dao.PersonRepository;
import word.shelf.dao.ShelfRepository;
import word.shelf.models.Person;
import word.shelf.models.Shelf;
import word.shelf.util.exceptions.FailedDeleteException;
import word.shelf.util.exceptions.FailedLoginException;
import word.shelf.util.responses.success.AuthResponse;
import word.shelf.util.responses.success.DeleteResponse;

import java.util.List;

@Service
public class AuthenticationService {

    private final PersonRepository personRepository;
    private final ShelfRepository shelfRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Autowired
    public AuthenticationService(PersonRepository personRepository, ShelfRepository shelfRepository, AuthenticationManager authenticationManager,
                                 TokenService tokenService) {
        this.personRepository = personRepository;
        this.shelfRepository = shelfRepository;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Transactional
    public AuthResponse registerUser(Person person) {
        int id = personRepository.save(person);
        try{
            Authentication auth = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(person.getName(), person.getPassword()));
            String token = tokenService.generateJwt(auth);
            shelfRepository.save(new Shelf(id, List.of()));
            return new AuthResponse(token, List.of());

        } catch(AuthenticationException | JsonProcessingException e){
            return new AuthResponse("", List.of());
        }
    }

    public AuthResponse loginUser(Person person){
        try{
            Authentication auth = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(person.getName(), person.getPassword()));
            String token = tokenService.generateJwt(auth);
            long id = tokenService.getIdFromToken(token);
            Shelf shelf = shelfRepository.findById(id).orElse(new Shelf(id, List.of()));

            return new AuthResponse(token, shelf.getJson());

        } catch(AuthenticationException e){
            throw new FailedLoginException();
        }
    }

    public DeleteResponse deletUser(HttpServletRequest request){
        String token = tokenService.extractTokenFromHeader(request);
        String name = tokenService.getNameFromToken(token);
        try{
            personRepository.deleteByName(name);
        } catch(DataAccessException e){
            throw new FailedDeleteException();
        }
        return new DeleteResponse("User with nickName '" + name + "' was deleted");
    }
}
