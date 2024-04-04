package word.shelf.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import word.shelf.dto.PersonDTO;
import word.shelf.dto.PersonLoginDTO;
import word.shelf.models.Person;
import word.shelf.services.AuthenticationService;
import word.shelf.util.PersonValidator;
import word.shelf.util.exceptions.FailedLoginException;
import word.shelf.util.exceptions.PersonAlreadyExistException;
import word.shelf.util.responses.success.AuthResponse;
import word.shelf.util.responses.success.DeleteResponse;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class PersonController {
    private final ModelMapper modelMapper;
    private final PersonValidator personValidator;
    private final AuthenticationService authenticationService;

    @Autowired
    public PersonController(ModelMapper modelMapper, PersonValidator personValidator, AuthenticationService authenticationService) {
        this.modelMapper = modelMapper;
        this.personValidator = personValidator;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> create(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {
        Person personToAdd = convertToPerson(personDTO);
        personValidator.validate(personToAdd, bindingResult);
       AuthResponse authResponse = authenticationService.registerUser(personToAdd);
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signing")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid PersonLoginDTO personDTO) {
        Person personToAdd = convertToPerson(personDTO);
        AuthResponse authResponse = authenticationService.loginUser(personToAdd);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<DeleteResponse> delete(HttpServletRequest request) {
        DeleteResponse response = authenticationService.deletUser(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }

    private Person convertToPerson(PersonLoginDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }

    @ExceptionHandler(PersonAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(PersonAlreadyExistException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("status", "error");
        errors.put("message", "Username already exists. Please choose a different login.");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(FailedLoginException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(FailedLoginException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("status", "error");
        errors.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }
}
