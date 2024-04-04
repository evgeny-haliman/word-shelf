package word.shelf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import word.shelf.dao.PersonRepository;
import word.shelf.security.PersonDetails;


public class PersonDetailsService implements UserDetailsService {
    private final PersonRepository personRepository;
    @Autowired
    public PersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        return new PersonDetails(personRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("User is not valid")));
    }
}
