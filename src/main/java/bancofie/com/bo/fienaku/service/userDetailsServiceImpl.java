package bancofie.com.bo.fienaku.service;

import bancofie.com.bo.fienaku.model.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import bancofie.com.bo.fienaku.repository.userRepository;

@Service
public class userDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private userRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String mail){
        user user = userRepository.findByMail(mail);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + mail);
        }
        return user;
    }
}
