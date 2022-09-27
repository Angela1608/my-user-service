package clear.solutions.userapp.service.impl;

import clear.solutions.userapp.model.User;
import clear.solutions.userapp.repository.UserRepository;
import clear.solutions.userapp.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Component
public class UserServiceImpl implements UserService {
    @Value("${legal.age}")
    private int legalAge;
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        int age = Period.between(user.getBirthDate(), LocalDate.now()).getYears();
        if (age >= legalAge) {
            return userRepository.save(user);
        }
        throw new RuntimeException("The age must be equal or greater then " + legalAge);
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<User> findAllByBirthDateBetween(LocalDate from, LocalDate to) {
        return userRepository.findAllByBirthDateBetween(from, to);
    }

    @Override
    public String deleteById(Long id) {

        userRepository.findById(id);
        return "SUCCESS";
    }
}
