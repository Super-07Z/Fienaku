package bancofie.com.bo.fienaku.controller;

import java.util.List;
import java.io.IOException;
import bancofie.com.bo.fienaku.dto.fienakuDTO;
import bancofie.com.bo.fienaku.error.apiError;
import bancofie.com.bo.fienaku.model.fienaku;
import bancofie.com.bo.fienaku.service.fienakuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/fienaku")
@RestController

public class fienakuController {

    private final fienakuService serviceFienaku;

    @Autowired
    public fienakuController(fienakuService serviceFienaku) {
        this.serviceFienaku = serviceFienaku;
    }
    
    @Operation(summary = "get list of all Fienakus")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "OK", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = fienaku.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = apiError.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = apiError.class)))
    })

    @PostMapping("/all")
    public ResponseEntity<List<fienaku>> getAll() {
        List<fienaku> allFienakus = serviceFienaku.getAll();
        return ResponseEntity.ok(allFienakus);
    }

    @PostMapping("/{id}")
    public ResponseEntity<fienaku> getOne(@PathVariable Long id) {
        fienaku fienaku = serviceFienaku.getOne(id);
        return ResponseEntity.ok(fienaku);
    }
    
    @Operation(summary = "Delete Fienaku")    
    @PostMapping("/delete/{id}")
    public ResponseEntity<fienaku> delete(@PathVariable Long id) {
        serviceFienaku.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Create Fienaku")
    @PostMapping("/create")
    public ResponseEntity<fienaku> createDTO(@RequestPart("fienaku") fienakuDTO dto, @RequestPart("file") MultipartFile file) throws IOException {
        fienaku createdFienaku = serviceFienaku.create(dto, file);
        return ResponseEntity.ok(createdFienaku);
    }
    
    @Operation(summary = "Update Fienaku")
    @PostMapping("/update/{id}")
    public ResponseEntity<fienaku> updateDTO(@PathVariable Long id, @RequestBody fienakuDTO dto) {
        fienaku updatedFienaku = serviceFienaku.update(id, dto);
        return ResponseEntity.ok(updatedFienaku);
    } 
}