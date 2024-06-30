package bancofie.com.bo.fienaku.service;

import bancofie.com.bo.fienaku.dto.userDTO;
import bancofie.com.bo.fienaku.model.fienaku;
import bancofie.com.bo.fienaku.model.user;
import bancofie.com.bo.fienaku.repository.chargeRepository;
import bancofie.com.bo.fienaku.repository.fienakuRepository;
import bancofie.com.bo.fienaku.repository.userRepository;
import bancofie.com.bo.fienaku.upload.storageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class userService {

    @Autowired
    private final fienakuRepository repositoryFienaku;
    @Autowired
    private final chargeRepository repositoryCharge;
    @Autowired
    private userRepository repositoryUser;
    @Autowired
    private final storageService serviceStorage;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Obtiene todos los usuarios.
     *
     * @return Lista de todos los usuarios.
     */
    public List<user> getAll() {
        return repositoryUser.findAll();
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id ID del usuario a buscar.
     * @return Usuario encontrado.
     * @throws RuntimeException si no se encuentra el usuario.
     */
    public user getOne(Long id) {
        return repositoryUser.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    /**
     * Registra un nuevo usuario.
     *
     * @param dto DTO con los datos del usuario a registrar.
     * @return Usuario registrado.
     */
    public user register(userDTO dto) {
        user data = new user();

        data.setUsername(dto.getUsername());
        data.setName(dto.getName());
        data.setLastname(dto.getLastname());
        data.setJob(dto.getJob());
        data.setFloor(dto.getFloor());
        data.setPhone(dto.getPhone());
        data.setMail(dto.getMail());
        data.setPassword(dto.getPassword());
        data.setAccount(dto.getAccount());
        data.setPassword(passwordEncoder.encode(data.getPassword()));

        return repositoryUser.save(data);
    }

    /**
     * Actualiza la imagen de perfil del usuario autenticado.
     *
     * @param file Archivo de imagen a subir.
     * @return Usuario con la imagen actualizada.
     * @throws IOException si hay un error al procesar el archivo.
     */
    @Transactional
    public user uploadImage(MultipartFile file) throws IOException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        user currentUser = repositoryUser.findByUsername(username);

        if (!file.isEmpty()) {
            String imageUrl = serviceStorage.store(file);
            currentUser.setImage(imageUrl);
        }

        return repositoryUser.save(currentUser);
    }

    /**
     * Actualiza la imagen de perfil de un usuario por su ID.
     *
     * @param userId ID del usuario.
     * @param file   Archivo de imagen a subir.
     * @return Usuario con la imagen actualizada.
     * @throws IOException si hay un error al procesar el archivo.
     */
    @Transactional
    public user uploadImageId(Long userId, MultipartFile file) throws IOException {
        user currentUser = repositoryUser.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!file.isEmpty()) {
            String imageUrl = serviceStorage.store(file);
            currentUser.setImage(imageUrl);
        }

        return repositoryUser.save(currentUser);
    }

    /**
     * Actualiza los datos del usuario autenticado.
     *
     * @param dto DTO con los datos actualizados del usuario.
     * @return Usuario con los datos actualizados.
     * @throws IOException si hay un error al procesar los datos.
     */
    @Transactional
    public user update(userDTO dto) throws IOException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        user updateUser = repositoryUser.findByUsername(username);

        updateUser.setUsername(dto.getUsername());
        updateUser.setName(dto.getName());
        updateUser.setLastname(dto.getLastname());
        updateUser.setJob(dto.getJob());
        updateUser.setFloor(dto.getFloor());
        updateUser.setPhone(dto.getPhone());
        updateUser.setMail(dto.getMail());
        updateUser.setPassword(dto.getPassword());
        updateUser.setAccount(dto.getAccount());

        return repositoryUser.save(updateUser);
    }

    /**
     * Actualiza los datos de un usuario por su ID.
     *
     * @param userId ID del usuario a actualizar.
     * @param dto    DTO con los datos actualizados del usuario.
     * @return Usuario con los datos actualizados.
     * @throws IOException si hay un error al procesar los datos.
     */
    @Transactional
    public user updateId(Long userId, userDTO dto) throws IOException {
        user currentUser = repositoryUser.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        currentUser.setName(dto.getName());
        currentUser.setLastname(dto.getLastname());
        currentUser.setMail(dto.getMail());
        currentUser.setAccount(dto.getAccount());
        currentUser.setPassword(passwordEncoder.encode(dto.getPassword()));

        return repositoryUser.save(currentUser);
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id ID del usuario a eliminar.
     * @throws RuntimeException si hay sorteos pendientes asociados a grupos de usuarios.
     */
    public void deleteID(Long id) {
        user userToDelete = repositoryUser.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        List<fienaku> fienakus = repositoryFienaku.findByUser(userToDelete);
        for (fienaku group : fienakus) {
            if (repositoryCharge.existsByFienaku(group)) {
                throw new RuntimeException("Cannot delete user because there are pending charge associated with group " + group.getId());
            }
        }
        repositoryUser.deleteById(id);
    }

    /**
     * Elimina el usuario autenticado.
     *
     * @throws RuntimeException si hay sorteos pendientes asociados a grupos de usuarios.
     */
    public void delete() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        user userToDelete = repositoryUser.findByUsername(username);

        List<fienaku> fienakus = repositoryFienaku.findByUser(userToDelete);
        for (fienaku group : fienakus) {
            if (repositoryCharge.existsByFienaku(group)) {
                throw new RuntimeException("Cannot delete user because there are pending charge associated with group " + group.getId());
            }
        }

        repositoryUser.deleteById(userToDelete.getId());
    }
    
    
}
