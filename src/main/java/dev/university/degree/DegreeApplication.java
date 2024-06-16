package dev.university.degree;

import dev.university.degree.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class DegreeApplication {
	@Autowired
	UserService userService;
	@Autowired
	JdbcUserDetailsManager jdbcUserDetailsManager;
	public static void main(String[] args) {
		SpringApplication.run(DegreeApplication.class, args);
	}
//	@Bean
//	ApplicationRunner applicationRunner(){
//		return app -> {
//			userService.registerNewUser("owner", "owner", "OWNER", null);
//		};
//	}
}
