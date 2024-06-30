package bancofie.com.bo.fienaku.service;

import java.io.*;
import java.util.*;
import java.util.stream.*;
import javax.transaction.Transactional;
import bancofie.com.bo.fienaku.model.*;
import bancofie.com.bo.fienaku.repository.*;
import bancofie.com.bo.fienaku.upload.storageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class chargeService {
        
    @Autowired
    private final storageService serviceStorage;
    @Autowired
    private final chargeRepository repositoryCharge;
    @Autowired
    private final fienakuRepository repositoryFienaku;
    @Autowired
    private final userRepository repositoryUser;
    
    /**
     * @return Lista de todos los cargos
     */
    public List<charge> getAll() {
        return repositoryCharge.findAll();
    }

    /**
     * @param id    ID del cobro a actualizar
     * @param file Archivo de imagen a almacenar
     * @return El cobro actualizado con la nueva imagen
     * @throws IOException Si ocurre un error al almacenar la imagen
     */
    @Transactional
    public charge update(Long id, @RequestPart("file") MultipartFile file) throws IOException {
        charge update = repositoryCharge.findById(id).orElseThrow(() -> new RuntimeException("Charge not found with ID " + id));

        if (!file.isEmpty()) {
            String imageUrl = serviceStorage.store(file);
            update.setImage(imageUrl);
        }

        return repositoryCharge.save(update);
    }

    /**
     * Cambia el estado de un cobro a "aceptado".
     *
     * @param id ID del cargo a actualizar
     * @return El cobro actualizado con el estado cambiado a "aceptado"
     */
    @Transactional
    public charge status(Long id) {
        charge update = repositoryCharge.findById(id).orElseThrow(() -> new RuntimeException("Cargo no encontrado con ID " + id));

        update.setStatus(true);

        return repositoryCharge.save(update);
    }

    /**
     * @return Lista de cobros aceptados del grupo
     * Utiliza streams de Java para filtrar y recopilar todas los cobros que tienen su estado marcado como "aceptado" (status = true) desde una lista de fienakus, usuarios y sus respectivos cobros.
     */
    public List<charge> accepted() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        user manager = repositoryUser.findByUsername(username);
        
        List<fienaku> fienakus = repositoryFienaku.findByUser(manager);

        return fienakus.stream()
                .flatMap(fienaku -> fienaku.getUser().stream())
                .flatMap(user -> user.getCharge().stream())
                .filter(charge::isStatus)
                .collect(Collectors.toList());
    }

    /**
     * @return Lista de cobros rechazados del grupo
     * Utiliza streams de Java para filtrar y recopilar todas los cobros que tienen su estado marcado como "rechazado" (status = false) desde una lista de fienakus, usuarios y sus respectivos cobros.
     */
    public List<charge> rejected() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        user manager = repositoryUser.findByUsername(username);
        
        List<fienaku> fienakus = repositoryFienaku.findByUser(manager);

        return fienakus.stream()
                .flatMap(fienaku -> fienaku.getUser().stream())
                .flatMap(user -> user.getCharge().stream())
                .filter(charge -> !charge.isStatus())
                .collect(Collectors.toList());
    }
    
    /**
     * @return Lista de Todos los cobros aceptados
     * Utiliza streams de Java para filtrar y recopilar todas los cobros que tienen su estado marcado como "aceptado" (status = true).
     */
    public List<charge> acceptedAll() {
        List<charge> allCharges = getAll();
        return allCharges.stream().filter(charge::isStatus).collect(Collectors.toList());
    }

    /**
     * @return Lista de Todos los cobros rechazados
     * Utiliza streams de Java para filtrar y recopilar todas los cobros que tienen su estado marcado como "aceptado" (status = false).
     */
    public List<charge> rejectedAll() {
        List<charge> allCharges = getAll();
        return allCharges.stream().filter(charge -> !charge.isStatus()).collect(Collectors.toList());
    }
}
