package bancofie.com.bo.fienaku.controller;

import java.util.List;
import java.io.IOException;

import bancofie.com.bo.fienaku.dto.userDTO;
import bancofie.com.bo.fienaku.model.user;
import bancofie.com.bo.fienaku.error.*;
import bancofie.com.bo.fienaku.model.fienaku;
import bancofie.com.bo.fienaku.service.fienakuService;
import bancofie.com.bo.fienaku.service.userService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

@RequestMapping("/user")
@RestController

public class userController {

    private final userService serviceUser;
    private final fienakuService serviceFienaku;

    @Autowired
    public userController(userService serviceUser,fienakuService serviceFienaku) {
        this.serviceUser = serviceUser;
        this.serviceFienaku = serviceFienaku;
    }

    @ApiResponses(value =
    {
        @ApiResponse(responseCode = "201", description = "OK", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = user.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = apiError.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = apiError.class)))
    })

    @Operation(summary = "List Users")
    @PostMapping("/all")
    public ResponseEntity<List<user>> getAll() {
        List<user> allFienakus = serviceUser.getAll();
        return ResponseEntity.ok(allFienakus);
    }
    @Operation(summary = "List a one user")
    @PostMapping("/{id}")
    public ResponseEntity<user> getOne(@PathVariable Long id) {
        user user = serviceUser.getOne(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Create User")
    @PostMapping("/register")
    public ResponseEntity<user> register(@RequestBody userDTO dto) {
        user newUser = serviceUser.register(dto);
        return ResponseEntity.ok(newUser);
    }
    
    @Operation(summary = "Upload Image")
    @PostMapping(value = "/uploadImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<user> uploadImage(@RequestPart("file") MultipartFile file) throws IOException {
        user updatedUser = serviceUser.uploadImage(file);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Upload Image with ID")
    @PostMapping(value = "/uploadImageID/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<user> uploadImageID(@PathVariable Long id, @RequestPart("file") MultipartFile file) throws IOException {
        user updatedUser = serviceUser.uploadImageId(id,file);
        return ResponseEntity.ok(updatedUser);
    }
    
    @Operation(summary = "Update User")
    @PostMapping(path = "/update")
    public ResponseEntity<user> update(@RequestBody userDTO dto) throws IOException {
        user updateUser = serviceUser.update(dto);
        return ResponseEntity.ok(updateUser);
    }

    @Operation(summary = "Update user with ID")
    @PostMapping(value = "/uploadImageID/{id}")
    public ResponseEntity<user> uploadUserID(@PathVariable Long id, @RequestBody userDTO dto) throws IOException {
        user updatedUser = serviceUser.updateId(id,dto);
        return ResponseEntity.ok(updatedUser);
    }
    
    @Operation(summary = "Delete User")
    @PostMapping("/delete")
    public ResponseEntity<user> delete(@PathVariable Long id) {
        serviceUser.delete();
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Delete User ID")
    @PostMapping("/delete/{id}")
    public ResponseEntity<user> deleteID(@PathVariable Long id) {
        serviceUser.deleteID(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("usuario/{id}/unirseGrupo")
    public ResponseEntity<?> addUserFienaku(@RequestParam Long id, @RequestParam String code){
        fienaku fienaku = serviceFienaku.getByCode(code);
        user user = serviceUser.getOne(id);
        fienaku.addUser(user);
        serviceFienaku.save(fienaku);
        return ResponseEntity.noContent().build();
    }
}