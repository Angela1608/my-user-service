package clear.solutions.userapp.service;

import clear.solutions.userapp.model.User;
import java.time.LocalDate;
import java.util.List;

public interface UserService {
    User save(User User);

    User getById(Long id);

    List<User> findAllByBirthDateBetween(LocalDate from, LocalDate to);

    String deleteById(Long id);
}
