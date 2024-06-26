package bancofie.com.bo.fienaku.controller;

import java.io.IOException;

import bancofie.com.bo.fienaku.error.apiError;
import bancofie.com.bo.fienaku.model.charge;
import bancofie.com.bo.fienaku.service.chargeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/charge")
@RestController
public class chargeController {
    
    private final chargeService serviceCharge;

    @Autowired
    public chargeController(chargeService serviceCharge) {
        this.serviceCharge = serviceCharge;
    }
    
    @Operation(summary = "get list of all charges")
    @ApiResponses(value =
    {
        @ApiResponse(responseCode = "201", description = "OK", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = charge.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = apiError.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = apiError.class)))
    })
    
    @PostMapping("/all")
    public ResponseEntity<List<charge>> getAll() {
        List<charge> allFienakus = serviceCharge.getAll();
        return ResponseEntity.ok(allFienakus);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<charge> update(@PathVariable Long id, @RequestPart("file") MultipartFile file) throws IOException {
        charge updatedCharge = serviceCharge.update(id, file);
        return ResponseEntity.ok(updatedCharge);
    }

    @PostMapping("/status/{id}")
    public ResponseEntity<charge> status(@PathVariable Long id) {
        charge updatedCharge = serviceCharge.status(id);
        return ResponseEntity.ok(updatedCharge);
    }

}
