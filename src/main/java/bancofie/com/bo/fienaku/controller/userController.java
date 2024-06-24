package bancofie.com.bo.fienaku.controller;

import java.util.List;
import java.io.IOException;

import bancofie.com.bo.fienaku.dto.userDTO;
import bancofie.com.bo.fienaku.model.user;
import bancofie.com.bo.fienaku.error.*;
import bancofie.com.bo.fienaku.service.userService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor

public class userController {

    private final userService serviceUser;

    @Autowired
    public userController(userService serviceUser) {
        this.serviceUser = serviceUser;
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

    @PostMapping("/{id}")
    public ResponseEntity<user> getOne(@PathVariable Long id) {
        user user = serviceUser.getOne(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Register User")
    @PostMapping("/register")
    public ResponseEntity<user> createDTO(@RequestPart("fienaku") userDTO dto, @RequestPart("file") MultipartFile file) throws IOException {
        user createdUser = serviceUser.create(dto, file);
        return ResponseEntity.ok(createdUser);
    }

    @Operation(summary = "Edit User")
    @PostMapping("/update/{id}")
    public ResponseEntity<user> updateDTO(@PathVariable Long id, @RequestBody userDTO dto, @RequestPart("file") MultipartFile file) throws IOException {
        user updateUser = serviceUser.update(id, dto, file);
        return ResponseEntity.ok(updateUser);
    }

    @Operation(summary = "Delete User")
    @PostMapping("/delete/{id}")
    public ResponseEntity<user> delete(@PathVariable Long id) {
        serviceUser.delete(id);
        return ResponseEntity.noContent().build();
    }
}