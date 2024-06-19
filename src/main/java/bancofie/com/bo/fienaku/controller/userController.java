package bancofie.com.bo.fienaku.controller;

import bancofie.com.bo.fienaku.error.apiError;
import bancofie.com.bo.fienaku.error.userNotFoundException;
import bancofie.com.bo.fienaku.model.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import bancofie.com.bo.fienaku.repository.userRepository;
import bancofie.com.bo.fienaku.upload.storageService;

@RestController
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
    @PostMapping("/user")
    public List<user> getAll() {
        try {
            return userRepository.findAll();
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving users");
        }
    }
    
    @Operation(summary = "List a user")
    @PostMapping("/user/{id}")
    public user getOne(@PathVariable Long id) {
        try {
            return userRepository.findById(id).orElseThrow(() -> new userNotFoundException(id));
        } catch (userNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }
    
    @Operation(summary = "Create User")
    @PostMapping(value = "/user/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> newUser(@RequestPart("user") user user, @RequestPart("file") MultipartFile file) {
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
        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(newUser));
    }
    
    @Operation(summary = "Edit User")
    @PostMapping("/user/edit/{id}")
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
    @PostMapping("/user/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        user user = userRepository.findById(id).orElseThrow(() -> new userNotFoundException(id));
        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }    
    
    @GetMapping("/homes")
    public String welcome() {
        return ("<h1>Welcome</h1>");
    }
    
    @GetMapping("/userr")
    public String user() {
        return ("<h1>Welcome user</h1>");
    }
    
    @GetMapping("/admin")
    public String admin() {
        return ("<h1>Welcome admin</h1>");
    }
    
    @GetMapping("/manager")
    public String manager() {
        return ("<h1>Welcome manager</h1>");
    }
}