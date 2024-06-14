package dev.university.degree.services;
import dev.university.degree.entities.Authority;
import dev.university.degree.entities.Employee;
import dev.university.degree.entities.User;
import dev.university.degree.repositories.AuthorityRepository;
import dev.university.degree.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;


    @Transactional
    public void registerNewUser(String username, String password, String role, Employee employee) {
        User user = new User();
        user.setUsername(username.trim());
        user.setPassword(passwordEncoder.encode(password.trim()));
        user.setEnabled(true);
        user.setEmployee(employee);

        Authority authority = new Authority();
        authority.setAuthority(role.toUpperCase());
        authority.setUser(user);
        authority.setUsername(user.getUsername());

        userRepository.save(user);
        authorityRepository.save(authority);
    }
}