package ru.job4j.url;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.domain.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserStore {
    private final ConcurrentHashMap<String, Person> users = new ConcurrentHashMap<>();

    public void save(Person person) {
        users.put(person.getUsername(), person);
    }


    public Person findByUsername(String username) {
        Person result = users.get(username);
        if (result == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Person is not found. Please, check requisites."
            );
        }
        return result;
    }

    public List<Person> findAll() {
        return new ArrayList<>(users.values());
    }
}
