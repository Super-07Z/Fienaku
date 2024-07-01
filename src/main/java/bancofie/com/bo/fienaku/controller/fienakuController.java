package bancofie.com.bo.fienaku.controller;

import java.util.List;
import java.io.IOException;
import bancofie.com.bo.fienaku.dto.*;
import bancofie.com.bo.fienaku.error.apiError;
import bancofie.com.bo.fienaku.exportExcel.fienakuExportExcel;
import bancofie.com.bo.fienaku.model.fienaku;
import bancofie.com.bo.fienaku.service.fienakuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

@RequestMapping("/fienaku")
@RestController
public class fienakuController {

    private final fienakuService serviceFienaku;
    private final fienakuExportExcel fienakuExportExcel;

    @Autowired
    public fienakuController(fienakuService serviceFienaku, fienakuExportExcel fienakuExportExcel) {
        this.serviceFienaku = serviceFienaku;
        this.fienakuExportExcel = fienakuExportExcel;
    }

    @Operation(summary = "Get list of all Fienakus")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = fienaku.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = apiError.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = apiError.class)))
    })
    @PostMapping("/all")
    public ResponseEntity<List<fienaku>> getAll() {
        List<fienaku> allFienakus = serviceFienaku.getAll();
        return ResponseEntity.ok(allFienakus);
    }

    @Operation(summary = "Get one Fienaku")
    @PostMapping("/{id}")
    public ResponseEntity<fienaku> getOne(@PathVariable Long id) {
        fienaku fienaku = serviceFienaku.getOne(id);
        return ResponseEntity.ok(fienaku);
    }

    @Operation(summary = "Register Fienaku")
    @PostMapping("/create")
    public ResponseEntity<fienaku> register(@RequestBody fienakuDTO dto) throws IOException {
        fienaku registerFienaku = serviceFienaku.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerFienaku);
    }

    @Operation(summary = "Update Fienaku")
    @PostMapping("/update/{id}")
    public ResponseEntity<fienaku> updateWithId(@PathVariable Long id, @RequestBody fienakuDTO dto) throws IOException {
        fienaku updatedFienaku = serviceFienaku.updateId(id, dto);
        return ResponseEntity.ok(updatedFienaku);
    }

    @Operation(summary = "Update Fienaku of authenticated manager")
    @PostMapping("/update")
    public ResponseEntity<fienaku> update(@RequestBody fienakuDTO dto) {
        fienaku updatedFienaku = serviceFienaku.update(dto);
        return ResponseEntity.ok(updatedFienaku);
    }

    @Operation(summary = "Upload Image")
    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<fienaku> uploadImage(@RequestPart("file") MultipartFile file) throws IOException {
        fienaku updatedFienaku = serviceFienaku.image(file);
        return ResponseEntity.ok(updatedFienaku);
    }

    @Operation(summary = "Upload Image with ID")
    @PostMapping(value = "/image/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<fienaku> uploadImageID(@PathVariable Long id, @RequestPart("file") MultipartFile file) throws IOException {
        fienaku updatedFienaku = serviceFienaku.imageId(id, file);
        return ResponseEntity.ok(updatedFienaku);
    }      
    
    @Operation(summary = "Delete Fienaku Id")
    @PostMapping("/delete/{id}")
    public ResponseEntity<fienaku> deleteId(@PathVariable Long id) {
        serviceFienaku.deleteId(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Shuffle Fienaku")
    @PostMapping("/shuffle")
    public ResponseEntity<List<chargeDTO>> shuffle() {
        List<chargeDTO> charges = serviceFienaku.shuffle();
        return ResponseEntity.ok(charges);
    }
    
    @Operation(summary = "Report Excel")
    @PostMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<fienaku> fienakus = serviceFienaku.getAll();
        fienakuExportExcel.export(fienakus, response);
    }
}
