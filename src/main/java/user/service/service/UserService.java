package user.service.service;

import java.time.LocalDate;
import java.util.List;
import user.service.model.User;

public interface UserService {
    User save(User user);

    User getById(Long id);

    List<User> findAllByBirthDateBetween(LocalDate from, LocalDate to);

    String deleteById(Long id);
}
