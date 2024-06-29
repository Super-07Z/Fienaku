package bancofie.com.bo.fienaku.controller;

import java.util.List;
import java.io.IOException;
import bancofie.com.bo.fienaku.dto.*;
import bancofie.com.bo.fienaku.error.apiError;
import bancofie.com.bo.fienaku.model.charge;
import bancofie.com.bo.fienaku.model.fienaku;
import bancofie.com.bo.fienaku.service.fienakuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

@RequestMapping("/fienaku")
@RestController

public class fienakuController {

    private final fienakuService serviceFienaku;

    @Autowired
    public fienakuController(fienakuService serviceFienaku) {
        this.serviceFienaku = serviceFienaku;
    }

    @Operation(summary = "get list of all Fienakus")
    @ApiResponses(value =
    {
        @ApiResponse(responseCode = "201", description = "OK", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = fienaku.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = apiError.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = apiError.class)))
    })

    @PostMapping("/all")
    public ResponseEntity <List<fienaku>> getAll() {
        List<fienaku> allFienakus = serviceFienaku.getAll();
        return ResponseEntity.ok(allFienakus);
    }

    @PostMapping("/{id}")
    public ResponseEntity <fienaku> getOne(@PathVariable Long id) {
        fienaku fienaku = serviceFienaku.getOne(id);
        return ResponseEntity.ok(fienaku);
    }

    @Operation(summary = "Register Fienaku")
    @PostMapping("/register")
    public ResponseEntity <fienaku> register(@RequestPart("fienaku") fienakuDTO dto, @RequestPart("file") MultipartFile file) throws IOException {
        fienaku registerFienaku = serviceFienaku.create(dto, file);
        return ResponseEntity.ok(registerFienaku);
    }

    @Operation(summary = "Update Fienaku")
    @PostMapping("/update/{id}")
    public ResponseEntity <fienaku> update(@PathVariable Long id, @RequestBody fienakuDTO dto, @RequestPart("file") MultipartFile file) throws IOException {
        fienaku updatedFienaku = serviceFienaku.update(id, dto, file);
        return ResponseEntity.ok(updatedFienaku);
    }

    @Operation(summary = "Delete Fienaku")
    @PostMapping("/delete/{id}")
    public ResponseEntity <fienaku> delete(@PathVariable Long id) {
        serviceFienaku.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/shuffle")
    public ResponseEntity<List<chargeDTO>> shuffle() {
        List<chargeDTO> charges = serviceFienaku.shuffle();
        return ResponseEntity.ok(charges);
    }
    
}