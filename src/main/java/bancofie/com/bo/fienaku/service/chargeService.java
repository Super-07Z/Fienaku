package bancofie.com.bo.fienaku.service;

import java.util.List;
import java.io.IOException;
import javax.transaction.Transactional;
import bancofie.com.bo.fienaku.model.charge;
import bancofie.com.bo.fienaku.repository.chargeRepository;
import bancofie.com.bo.fienaku.upload.storageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Service
public class chargeService {
        
    private final storageService serviceStorage;
    private final chargeRepository repositoryCharge;
    
    @Autowired
    public chargeService(storageService serviceStorage, chargeRepository repositoryCharge){
        this.serviceStorage = serviceStorage;
        this.repositoryCharge = repositoryCharge;
    }
    
    public List<charge> getAll() {
        return repositoryCharge.findAll();
    }
        
    @Transactional
    public charge update(Long id, @RequestPart("file") MultipartFile file) throws IOException {
        charge update = repositoryCharge.findById(id).orElseThrow(() -> new RuntimeException("Charge not found with id " + id));      
        
        if (!file.isEmpty()) {
            String imageUrl = serviceStorage.store(file);
            update.setImage(imageUrl);
        }

        return repositoryCharge.save(update);
    }
    
    @Transactional
    public charge status(Long id) {
        charge update = repositoryCharge.findById(id).orElseThrow(() -> new RuntimeException("Charge not found with id " + id));
        
        update.setStatus(true);

        return repositoryCharge.save(update);
    }
}
