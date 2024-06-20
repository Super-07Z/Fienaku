package bancofie.com.bo.fienaku.service;

import bancofie.com.bo.fienaku.dto.fienakuDTO;

import bancofie.com.bo.fienaku.model.*;
import bancofie.com.bo.fienaku.repository.*;
import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class fienakuService {

    private fienakuRepository repositoryFienaku;
    private userRepository repositoryUser;

    public List<fienaku> getAll() {
        return repositoryFienaku.findAll();
    }
        
    public fienaku getOne(Long id) {
        return repositoryFienaku.findById(id)
                .orElseThrow(() -> new RuntimeException("fienaku not found with id " + id));
    }
    
    public fienaku create(fienakuDTO dto) {
        fienaku data = new fienaku();
        data.setName(dto.getName());
        data.setCode(dto.getCode());
        data.setMount(dto.getMount());
        data.setPenitence(dto.getPenitence());
        data.setTimespan(dto.getTimespan());
        fienaku savedFienaku = repositoryFienaku.save(data);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        user userManager = repositoryUser.findByMail(username);
        savedFienaku.addUser(userManager);
        repositoryFienaku.save(savedFienaku);
        userManager.setUsertype(userType.ROLE_MANAGER);
        repositoryUser.save(userManager);
        return savedFienaku;
    }

    public fienaku update(Long id, fienakuDTO dto) {
        fienaku update = repositoryFienaku.findById(id)
                .orElseThrow(() -> new RuntimeException("fienaku not found with id " + id));
        update.setName(dto.getName());
        update.setCode(dto.getCode());
        update.setMount(dto.getMount());
        update.setPenitence(dto.getPenitence());
        update.setTimespan(dto.getTimespan());

        return repositoryFienaku.save(update);
    }

    public void delete(Long id) {
        fienaku existingFienaku = repositoryFienaku.findById(id)
                .orElseThrow(() -> new RuntimeException("fienaku not found with id " + id));

        repositoryFienaku.delete(existingFienaku);
    }
    


}
