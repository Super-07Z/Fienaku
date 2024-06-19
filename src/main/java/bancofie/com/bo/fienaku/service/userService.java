package bancofie.com.bo.fienaku.service;

import bancofie.com.bo.fienaku.model.user;
import bancofie.com.bo.fienaku.repository.userRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class userService {

    @Autowired
    private userRepository userRepository;

    @Autowired
    private PasswordEncoder PasswordEncoder;

    public void save(user user) {
        user.setPassword(PasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}