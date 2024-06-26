package dev.university.degree.repositories;

import dev.university.degree.entities.Employee;
import dev.university.degree.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    User findByEmployee(Employee employee);
    boolean existsByUsername(String username);
}
