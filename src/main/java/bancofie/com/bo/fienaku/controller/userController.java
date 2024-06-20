package bancofie.com.bo.fienaku.controller;
import java.util.List;
import bancofie.com.bo.fienaku.model.user;
import bancofie.com.bo.fienaku.upload.storageService;
import bancofie.com.bo.fienaku.error.*;
import bancofie.com.bo.fienaku.repository.userRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor

public class userController {
    
    private final userRepository userRepository;
    private final storageService storageService;
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "OK", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = user.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = apiError.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = apiError.class)))
    })
    
    @Operation(summary = "List Users")
    @PostMapping("/all")
    public List<user> getAll() {
        try {
            return userRepository.findAll();
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving users");
        }
    }
    
    @Operation(summary = "List a user")
    @PostMapping("/{id}")
    public user getOne(@PathVariable Long id) {
        try {
            return userRepository.findById(id).orElseThrow(() -> new userNotFoundException(id));
        } catch (userNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }
    
    @Operation(summary = "Create User")
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> newUser(@ModelAttribute user user, @RequestParam("file") MultipartFile file) {
        try {
            String urlImagen = null;
            if (!file.isEmpty()) {
                String imagen = storageService.store(file);
                urlImagen = MvcUriComponentsBuilder.fromMethodName(storageController.class, "serveFile", imagen, null).build().toUriString();
            }

            user newUser = new user();
            newUser.setName(user.getName());
            newUser.setLastname(user.getLastname());
            newUser.setMail(user.getMail());
            newUser.setPassword(user.getPassword());
            newUser.setAccount(user.getAccount());
            newUser.setImage(urlImagen);
            newUser.setUsertype(user.getUsertype());

            user savedUser = userRepository.save(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el usuario: " + e.getMessage());
        }
    }

    @Operation(summary = "Edit User")
    @PostMapping("/edit/{id}")
    public user editUser(@RequestBody user edit, @PathVariable Long id) {
        return userRepository.findById(id).map(p -> {
            p.setName(edit.getName());
            p.setLastname(edit.getLastname());
            p.setMail(edit.getMail());
            p.setPassword(edit.getPassword());
            p.setAccount(edit.getAccount());
            return userRepository.save(p);
        }).orElseThrow(() -> new userNotFoundException(id));
    }
    
    @Operation(summary = "Delete User")
    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        user user = userRepository.findById(id).orElseThrow(() -> new userNotFoundException(id));
        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }    
}