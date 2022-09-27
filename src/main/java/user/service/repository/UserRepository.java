package user.service.repository;

import user.service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("from User u where u.birthDate between ?1 and ?2")
    List<User> findAllByBirthDateBetween(LocalDate from, LocalDate to);
}
