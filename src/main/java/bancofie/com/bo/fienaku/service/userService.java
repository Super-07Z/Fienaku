package bancofie.com.bo.fienaku.service;

import bancofie.com.bo.fienaku.dto.userDTO;
import java.util.*;

import bancofie.com.bo.fienaku.model.user;
import bancofie.com.bo.fienaku.repository.userRepository;
import bancofie.com.bo.fienaku.upload.storageService;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Service
public class userService {

    private userRepository repositoryUser;
    private final storageService serviceStorage;
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    public userService(userRepository repositoryUser, storageService serviceStorage, PasswordEncoder passwordEncoder){
        this.repositoryUser = repositoryUser;
        this.serviceStorage = serviceStorage;
        this.passwordEncoder = passwordEncoder;
   }
    
    public List<user> getAll() {
        return repositoryUser.findAll();
    }

    public user getOne(Long id) {
        return repositoryUser.findById(id)
                .orElseThrow(() -> new RuntimeException("fienaku not found with id " + id));
    }

    public user register(@RequestPart("user") userDTO dto, @RequestPart("file") MultipartFile file) throws IOException {
        user data = new user();
        data.setName(dto.getName());
        data.setLastname(dto.getLastname());
        data.setMail(dto.getMail());
        data.setPassword(dto.getPassword());
        data.setAccount(dto.getAccount());

        if (!file.isEmpty())
        {
            String imageUrl = serviceStorage.store(file);
            data.setImage(imageUrl);
        }

        data.setPassword(passwordEncoder.encode(data.getPassword()));

        user savedUser = repositoryUser.save(data);
        return savedUser;
    }

    public user update(Long id, userDTO dto, @RequestPart("file") MultipartFile file) throws IOException {
        user update = repositoryUser.findById(id) 
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));

        update.setName(dto.getName());
        update.setLastname(dto.getLastname());
        update.setMail(dto.getMail());
        update.setAccount(dto.getAccount());
        update.setPassword(passwordEncoder.encode(dto.getPassword()));

        if (!file.isEmpty()) {
            String imageUrl = serviceStorage.store(file);
            update.setImage(imageUrl);
        }

        return repositoryUser.save(update);
    }
        
    public void delete(Long id) {
        repositoryUser.deleteById(id);
    }
}
