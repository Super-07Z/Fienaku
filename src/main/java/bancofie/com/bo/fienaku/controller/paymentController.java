package bancofie.com.bo.fienaku.controller;

import java.io.IOException;

import bancofie.com.bo.fienaku.model.payment;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/payment")
@RestController
public class paymentController {
    
    @Operation(summary = "Update Payment")
    @PostMapping("/update/{id}")
    public ResponseEntity<payment> update(@PathVariable Long id, @RequestBody payment pay, @RequestPart("file") MultipartFile file) throws IOException {
        fienaku updatedFienaku = serviceFienaku.update(id, dto, file);
        return ResponseEntity.ok(updatedFienaku);
    }
}
