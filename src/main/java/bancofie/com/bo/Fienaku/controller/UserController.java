package bancofie.com.bo.Fienaku.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import lombok.RequiredArgsConstructor;

import bancofie.com.bo.Fienaku.model.User;
import bancofie.com.bo.Fienaku.model.UserRepository;
import bancofie.com.bo.Fienaku.error.ApiError;
import bancofie.com.bo.Fienaku.error.UserNotFoundException;
import bancofie.com.bo.Fienaku.upload.StorageService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final StorageService storageService;

    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "OK", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = User.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ApiError.class)))
    })

    @Operation(summary = "List Users")
    @PostMapping("/user")
    public List<User> getAll() {
        try {
            return userRepository.findAll();
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving users");
        }
    }

    @Operation(summary = "List a one User")
    @PostMapping("/user/{id}")
    public User getOne(@PathVariable Long id) {
        try {
            return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @Operation(summary = "Create user")
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> newUser(@RequestPart("user") User user, @RequestPart("file") MultipartFile file) {
        String urlImagen = null;

        if (!file.isEmpty()) {
            String imagen = storageService.store(file);
            urlImagen = MvcUriComponentsBuilder.fromMethodName(StorageController.class, "serveFile", imagen, null).build().toUriString();
        }

        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setLastname(user.getLastname());
        newUser.setMail(user.getMail());
        newUser.setPassword(user.getPassword());
        newUser.setImage(urlImagen);

        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(newUser));
    }

    @Operation(summary = "edit user")
    @PostMapping("/edit/{id}")
    public User editUser(@RequestBody User edit, @PathVariable Long id) {
        return userRepository.findById(id).map(p -> {
            p.setName(edit.getName());
            p.setLastname(edit.getLastname());
            p.setMail(edit.getMail());
            p.setPassword(edit.getPassword());
            return userRepository.save(p);
        }).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Operation(summary = "delete user")
    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }

}
