package ru.job4j.url;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.domain.Person;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserStore users;
    private BCryptPasswordEncoder encoder;
    private final ObjectMapper objectMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getSimpleName());

    public UserController(UserStore users,
                          BCryptPasswordEncoder encoder, ObjectMapper objectMapper) {
        this.users = users;
        this.encoder = encoder;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody Person person) {
        if ("123".equals(person.getPassword())) {
            throw new IllegalArgumentException("incorrect password");
        }
        if (person.getPassword() == null) {
            throw new NullPointerException("password can't be null");
        }
        person.setPassword(encoder.encode(person.getPassword()));
        users.save(person);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Person>> findAll() {

        return ResponseEntity.ok(users.findAll());
    }

    @ExceptionHandler (value = {IllegalArgumentException.class})
    public void handleException(Exception e, HttpServletRequest request,
                                HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter()
                .write(objectMapper.writeValueAsString( new HashMap<>() {
                    {
                        put("message", e.getMessage());
                        put("type", e.getClass());
                    }                 }
                ));
    }


}
