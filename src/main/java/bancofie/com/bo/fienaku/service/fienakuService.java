package bancofie.com.bo.fienaku.service;

import java.util.*;
import java.io.IOException;
import javax.transaction.Transactional;

import bancofie.com.bo.fienaku.dto.*;
import bancofie.com.bo.fienaku.model.*;
import bancofie.com.bo.fienaku.repository.*;
import bancofie.com.bo.fienaku.upload.storageService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Service

public class fienakuService {

    private final fienakuRepository repositoryFienaku;
    private final chargeRepository repositoryCharge;
    private final userRepository repositoryUser;
    private final storageService serviceStorage;

    @Autowired
    public fienakuService(fienakuRepository repositoryFienaku, userRepository repositoryUser, storageService serviceStorage, chargeRepository repositoryCharge) {
        this.repositoryFienaku = repositoryFienaku;
        this.repositoryUser = repositoryUser;
        this.serviceStorage = serviceStorage;
        this.repositoryCharge = repositoryCharge;
    }

    public List<fienaku> getAll() {
        return repositoryFienaku.findAll();
    }

    public fienaku getOne(Long id) {
        return repositoryFienaku.findById(id).orElseThrow(() -> new RuntimeException("fienaku not found with id " + id));
    }

    @Transactional
    public fienaku create(fienakuDTO dto, MultipartFile file) throws IOException {
        
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        user userManager = repositoryUser.findByMail(username);
        
        fienaku data = new fienaku();
        data.setName(dto.getName());
        data.setCode(dto.getCode());
        data.setMount(dto.getMount());
        data.setPenitence(dto.getPenitence());
        data.setTimespan(dto.getTimespan());

        if (!file.isEmpty()){
            String imageUrl = serviceStorage.store(file);
            data.setImage(imageUrl);
        }

        fienaku savedFienaku = repositoryFienaku.save(data);

        savedFienaku.addUser(userManager);
        
        userManager.setUsertype(userType.ROLE_MANAGER);
        repositoryUser.save(userManager);

        return savedFienaku;
    }

    public fienaku update(Long id, fienakuDTO dto, @RequestPart("file") MultipartFile file) throws IOException {
        
        fienaku update = repositoryFienaku.findById(id).orElseThrow(() -> new RuntimeException("fienaku not found with id " + id));

        update.setName(dto.getName());
        update.setCode(dto.getCode());
        update.setMount(dto.getMount());
        update.setPenitence(dto.getPenitence());
        update.setTimespan(dto.getTimespan());

        if (!file.isEmpty()){
            String imageUrl = serviceStorage.store(file);
            update.setImage(imageUrl);
        }

        return repositoryFienaku.save(update);
    }

    public void delete(Long id) {
        repositoryFienaku.deleteById(id);
    }

    public Date calculateDate(Date date, int span) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, span);

        return calendar.getTime();
    }

    public List<user> sort(List<user> users) {
        List<user> list = new LinkedList<>(users);

        Collections.shuffle(list);
        return list;
    }

    public List<Date> calculateCharge(Date create, int span, int j) {
        List<Date> paymentDates = new LinkedList<>();

        Date date = create;

        for (int i = 0; i < j; i++){
            date = calculateDate(date, span);
            paymentDates.add(date);
        }

        return paymentDates;
    }
    
    public List<chargeDTO> shuffle() {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String mail = userDetails.getUsername();

        user manager = repositoryUser.findByMail(mail);

        List<fienaku> users = repositoryFienaku.findByUser(manager);

        List<chargeDTO> registerCharge = new ArrayList<>();

        for (fienaku data : users){

            if (repositoryCharge.existsByFienaku(data)){
                throw new RuntimeException("This fienaku has registered payments");
            }

            List<user> sortUsers = sort(data.getUser());
            int span = data.getTimespan();
            List<Date> charge = calculateCharge(data.getCreate(), span, sortUsers.size());

            for (int i = 0; i < sortUsers.size(); i++){
                charge charx = new charge();
                charx.setFienaku(data);
                charx.setUser(sortUsers.get(i));
                charx.setAccount(sortUsers.get(i).getAccount());
                charx.setDate(charge.get(i));
                charx.setMount(data.getMount());

                charge saveCharge = repositoryCharge.save(charx);

                registerCharge.add(chargeDTO.charge(saveCharge));
            }
        }
        return registerCharge;
    }

}
