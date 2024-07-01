package bancofie.com.bo.fienaku.service;

import java.util.*;
import java.io.IOException;
import javax.transaction.Transactional;

import bancofie.com.bo.fienaku.dto.*;
import bancofie.com.bo.fienaku.model.*;
import bancofie.com.bo.fienaku.repository.*;
import bancofie.com.bo.fienaku.upload.storageService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class fienakuService {

    @Autowired
    private final fienakuRepository repositoryFienaku;
    @Autowired
    private final chargeRepository repositoryCharge;
    @Autowired
    private final userRepository repositoryUser;
    @Autowired
    private final storageService serviceStorage;
    
    /**
     * @return Una lista de todos los Fienaku.
     */
    public List<fienaku> getAll() {
        return repositoryFienaku.findAll();
    }
    /**
     * @param id Obtiene un fienaku por su ID.
     * @return El fienaku encontrado
     * @throws RuntimeException si no se encuentra ningún fienaku con el ID
     * especificado
     */
    public fienaku getOne(Long id) {
        return repositoryFienaku.findById(id).orElseThrow(() -> new RuntimeException("fienaku not found with id " + id));
    }
    /**
     * Crea un nuevo fienaku basado en los datos proporcionados en el DTO y
     * guarda en la base de datos.
     *
     * @param dto DTO con los datos del fienaku a crear.
     * @return El fienaku creado y guardado.
     */
    @Transactional
    public fienaku create(fienakuDTO dto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        user userManager = repositoryUser.findByUsername(username);

        fienaku data = new fienaku();
        data.setName(dto.getName());
        data.setCode(dto.getCode());
        data.setMount(dto.getMount());
        data.setPenitence(dto.getPenitence());
        data.setTimespan(dto.getTimespan());

        fienaku savedFienaku = repositoryFienaku.save(data);
        savedFienaku.addUser(userManager);

        userManager.setUsertype(userType.ROLE_MANAGER);
        repositoryUser.save(userManager);

        return savedFienaku;
    }
    /**
     * Actualiza un fienaku existente por su ID con los datos proporcionados en
     * el DTO y opcionalmente una nueva imagen.
     *
     * @param id ID del fienaku a actualizar
     * @param dto DTO con los nuevos datos del fienaku
     * @return El fienaku actualizado
     * @throws RuntimeException si no se encuentra ningún fienaku con el ID
     * especificado
     */
    @Transactional
    public fienaku updateId(Long id, fienakuDTO dto) {
        fienaku update = repositoryFienaku.findById(id).orElseThrow(() -> new RuntimeException("fienaku not found with id " + id));

        update.setName(dto.getName());
        update.setCode(dto.getCode());
        update.setMount(dto.getMount());
        update.setPenitence(dto.getPenitence());
        update.setTimespan(dto.getTimespan());

        return repositoryFienaku.save(update);
    }
    /**
     * @param dto DTO con los nuevos datos del fienaku.
     * @return El fienaku actualizado.
     * @throws RuntimeException si no se encuentra ningún fienaku para el manager autenticado.
     */
    @Transactional
    public fienaku update(fienakuDTO dto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        user manager = repositoryUser.findByUsername(username);

        fienaku fienakuToUpdate = repositoryFienaku.findByUser(manager).stream().findFirst().orElseThrow(() -> new RuntimeException("No fienaku found for the authenticated manager"));

        fienakuToUpdate.setName(dto.getName());
        fienakuToUpdate.setCode(dto.getCode());
        fienakuToUpdate.setMount(dto.getMount());
        fienakuToUpdate.setPenitence(dto.getPenitence());
        fienakuToUpdate.setTimespan(dto.getTimespan());

        return repositoryFienaku.save(fienakuToUpdate);
    }
    /**
     * @param id El ID del fienaku a actualizar.
     * @param image Archivo de imagen para actualizar la imagen del fienaku.
     * @return El fienaku actualizado con la nueva imagen.
     * @throws IOException si hay un error al manipular el archivo.
     * @throws RuntimeException si no se encuentra ningún fienaku con el ID especificado.
     */
    @Transactional
    public fienaku imageId(Long id, MultipartFile image) throws IOException {
        fienaku update = repositoryFienaku.findById(id).orElseThrow(() -> new RuntimeException("fienaku not found with id " + id));

        if (!image.isEmpty()) {
            String imageUrl = serviceStorage.store(image);
            update.setImage(imageUrl);
        }

        return repositoryFienaku.save(update);
    }

    /**
     * Actualiza la imagen del fienaku del manager autenticado.
     *
     * @param image Archivo de imagen para actualizar la imagen del fienaku.
     * @return El fienaku actualizado con la nueva imagen.
     * @throws IOException si hay un error al manipular el archivo.
     * @throws RuntimeException si no se encuentra ningún fienaku para el manager autenticado.
     */
    @Transactional
    public fienaku image(MultipartFile image) throws IOException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        user manager = repositoryUser.findByUsername(username);

        fienaku fienakuToUpdate = repositoryFienaku.findByUser(manager).stream().findFirst().orElseThrow(() -> new RuntimeException("No fienaku found for the authenticated manager"));

        if (!image.isEmpty()) {
            String imageUrl = serviceStorage.store(image);
            fienakuToUpdate.setImage(imageUrl);
        }

        return repositoryFienaku.save(fienakuToUpdate);
    }
    /**
     * @param id El ID del fienaku a eliminar.
     * @throws RuntimeException si el fienaku tiene cobros activos.
     */
    @Transactional
    public void deleteId(Long id) {
        repositoryFienaku.deleteById(id);
    }
    /**
     * @param date Fecha inicial.
     * @param days Número de días a agregar.
     * @return La nueva fecha con los días agregados.
     */
    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }
    /**
     * @param users Lista de usuarios a barajear.
     * @return La lista barajeada de usuarios.
     */
    public List<user> sort(List<user> users) {
        List<user> list = new LinkedList<>(users);
        Collections.shuffle(list);
        return list;
    }

    /**
     * Calcula fechas de cobro basadas en una fecha inicial y un intervalo de días.
     *
     * @param create Fecha inicial para el cálculo de cobros.
     * @param span Número de días entre cobros.
     * @param j Número de fechas de cobro a calcular.
     * @return Una lista de fechas de cobro calculadas.
     */
    public List<Date> calculateCharge(Date create, int span, int j) {
        List<Date> paymentDates = new LinkedList<>();
        Date date = create;
        for (int i = 0; i < j; i++) {
            date = addDays(date, span);
            paymentDates.add(date);
        }
        return paymentDates;
    }

    /**
     * Baraja usuarios asociados a Fienaku, calcula fechas de pago, registra
     * cobros, y devuelve una lista de cargos registrados como DTOs.
     *
     * @return Una lista de cobrosDTO representando los cobros registrados
     * @throws RuntimeException si un fienaku tiene pagos pendientes
     */
    public List<chargeDTO> shuffle() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        user manager = repositoryUser.findByUsername(username);

        List<fienaku> users = repositoryFienaku.findByUser(manager);
        List<chargeDTO> registerCharge = new ArrayList<>();
        for (fienaku data : users)
        {
            if (repositoryCharge.existsByFienaku(data))
            {
                throw new RuntimeException("This fienaku has a pending draw");
            }
            List<user> sortUsers = sort(data.getUser());
            int span = data.getTimespan();
            List<Date> charge = calculateCharge(data.getCreate(), span, sortUsers.size());
            for (int i = 0; i < sortUsers.size(); i++)
            {
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
    /**
     * @param code Agrega un Usuario con el codigo del Fienaku
     */
    @Transactional
    public fienaku getByCode(String code) {
        return repositoryFienaku.findByCode(code);
    }
    /**
     * Guarda un fienaku en la base de datos.
     *
     * @param fienaku El fienaku a guardar.
     */
    @Transactional
    public void save(fienaku fienaku) {
        repositoryFienaku.save(fienaku);
    }
}
