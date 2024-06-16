package dev.university.degree.repositories;

import dev.university.degree.entities.Authority;
import dev.university.degree.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByUser(User user);
}
