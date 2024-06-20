package bancofie.com.bo.fienaku.controller;

import bancofie.com.bo.fienaku.dto.fienakuDTO;
import bancofie.com.bo.fienaku.error.apiError;
import bancofie.com.bo.fienaku.model.fienaku;

import bancofie.com.bo.fienaku.service.fienakuService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/fienaku")
@RestController
@RequiredArgsConstructor

public class fienakuController {

    private final fienakuService fienaxService;

    @Operation(summary = "get list of all Fienakus")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "OK", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = fienaku.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = apiError.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = apiError.class)))
    })

    @PostMapping("/all")
    public ResponseEntity<List<fienaku>> getAll() {
        List<fienaku> allFienakus = fienaxService.getAll();
        return ResponseEntity.ok(allFienakus);
    }
    
    @PostMapping("/delete/{id}")
    public ResponseEntity<fienaku> delete(@PathVariable Long id) {
        fienaxService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/create")
    public ResponseEntity<fienaku> createDTO(@RequestBody fienakuDTO dto) {
        fienaku createdFienaku = fienaxService.create(dto);
        return ResponseEntity.ok(createdFienaku);
    }  
    
    @PostMapping("/update/{id}")
    public ResponseEntity<fienaku> updateDTO(@PathVariable Long id, @RequestBody fienakuDTO dto) {
        fienaku updatedFienaku = fienaxService.update(id, dto);
        return ResponseEntity.ok(updatedFienaku);
    }    
    
}