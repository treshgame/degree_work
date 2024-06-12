package dev.university.degree.services;

import dev.university.degree.util.Job;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import dev.university.degree.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        dev.university.degree.entities.User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        String role = "";
        if(user.getEmployee() == null){
            role = "OWNER";
        }else if(user.getEmployee().getJob() == Job.VET){
            role = "VET";
        }else if(user.getEmployee().getJob() == Job.ADMINISTRATOR){
            role = "ADMINISTRATOR";
        }else{
            role = "INPATIENT";
        }

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(role)
                .build();
    }
}