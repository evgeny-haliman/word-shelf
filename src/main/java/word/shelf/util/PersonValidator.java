package word.shelf.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import word.shelf.dao.PersonRepository;
import word.shelf.models.Person;
import word.shelf.util.exceptions.PersonAlreadyExistException;

@Component
public class PersonValidator implements Validator {
    private final PersonRepository personRepository;

    @Autowired
    public PersonValidator(PersonRepository personDao) {
        this.personRepository = personDao;
    }

    @Override
    public boolean supports(@NonNull Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(@NonNull Object object, @Nullable Errors errors) {
        Person person = (Person) object;

        if (personRepository.findByName(person.getName()).isPresent()) {
            assert errors != null;
            errors.rejectValue("name", "User with this name is already present!");
            throw new PersonAlreadyExistException();
        }
    }
}
