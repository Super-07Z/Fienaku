package bancofie.com.bo.fienaku.service;

import java.util.List;
import bancofie.com.bo.fienaku.model.*;
import bancofie.com.bo.fienaku.repository.*;
import bancofie.com.bo.fienaku.dto.fienakuDTO;
import bancofie.com.bo.fienaku.upload.storageService;
import java.io.IOException;
import javax.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Service
public class fienakuService {

    private final fienakuRepository repositoryFienaku;
    private final userRepository repositoryUser;
    private final storageService serviceStorage;

    @Autowired
    public fienakuService(fienakuRepository repositoryFienaku, userRepository repositoryUser, storageService serviceStorage) {
        this.repositoryFienaku = repositoryFienaku;
        this.repositoryUser = repositoryUser;
        this.serviceStorage = serviceStorage;
    }

    public List<fienaku> getAll() {
        return repositoryFienaku.findAll();
    }

    public fienaku getOne(Long id) {
        return repositoryFienaku.findById(id)
                .orElseThrow(() -> new RuntimeException("fienaku not found with id " + id));
    }

    @Transactional
    public fienaku create(fienakuDTO dto, MultipartFile file) throws IOException {
        fienaku data = new fienaku();
        data.setName(dto.getName());
        data.setCode(dto.getCode());
        data.setMount(dto.getMount());
        data.setPenitence(dto.getPenitence());
        data.setTimespan(dto.getTimespan());

        if (!file.isEmpty())
        {
            String imageUrl = serviceStorage.store(file);
            data.setImage(imageUrl);
        }

        fienaku savedFienaku = repositoryFienaku.save(data);

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        user userManager = repositoryUser.findByMail(username);

        savedFienaku.addUser(userManager);
        userManager.setUsertype(userType.ROLE_MANAGER);
        repositoryUser.save(userManager);

        return savedFienaku;
    }

    public fienaku update(Long id, fienakuDTO dto, @RequestPart("file") MultipartFile file) throws IOException {
        fienaku update = repositoryFienaku.findById(id)
                .orElseThrow(() -> new RuntimeException("fienaku not found with id " + id));
        
        update.setName(dto.getName());
        update.setCode(dto.getCode());
        update.setMount(dto.getMount());
        update.setPenitence(dto.getPenitence());
        update.setTimespan(dto.getTimespan());
        
        if (!file.isEmpty()) {
            String imageUrl = serviceStorage.store(file);
            update.setImage(imageUrl);
        }
        
        return repositoryFienaku.save(update);
    }

    public void delete(Long id) {
        repositoryFienaku.deleteById(id);
    }
}
