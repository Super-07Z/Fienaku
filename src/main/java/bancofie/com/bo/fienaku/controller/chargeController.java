package bancofie.com.bo.fienaku.controller;

import java.util.List;
import java.io.IOException;
import bancofie.com.bo.fienaku.error.apiError;
import bancofie.com.bo.fienaku.exportExcel.chargeExportExcel;
import bancofie.com.bo.fienaku.model.*;
import bancofie.com.bo.fienaku.service.chargeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/charge")
@RestController
public class chargeController {
    
    private final chargeService serviceCharge;
    private final chargeExportExcel chargeExportExcel;

    @Autowired
    public chargeController(chargeService serviceCharge,chargeExportExcel chargeExportExcel) {
        this.serviceCharge = serviceCharge;
        this.chargeExportExcel = chargeExportExcel;
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

    @Operation(summary = "Updload Image")
    @PostMapping(path ="/update/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<charge> update(@PathVariable Long id, @RequestPart("file") MultipartFile file) throws IOException {
        charge updatedCharge = serviceCharge.update(id, file);
        return ResponseEntity.ok(updatedCharge);
    }

    @Operation(summary = "Status Charge")
    @PostMapping("/status/{id}")
    public ResponseEntity<charge> status(@PathVariable Long id) {
        charge updatedCharge = serviceCharge.status(id);
        return ResponseEntity.ok(updatedCharge);
    }

    @Operation(summary = "Status = true Charge Fienaku")
    @PostMapping("/accepted")
    public ResponseEntity<List<charge>> acceptedCharge() {
        List<charge> acceptedCharge = serviceCharge.accepted();
        return ResponseEntity.ok(acceptedCharge);
    }
    
    @Operation(summary = "Status = false Charge Fienaku")
    @PostMapping("/rejected")
    public ResponseEntity<List<charge>> rejectedCharge() {
        List<charge> rejectedCharge = serviceCharge.rejected();
        return ResponseEntity.ok(rejectedCharge);
    }
    
    @Operation(summary = "Status = true all Fienaku")
    @PostMapping("/acceptedAll")
    public ResponseEntity<List<charge>> acceptedAllCharge() {
        List<charge> acceptedAllCharge = serviceCharge.acceptedAll();
        return ResponseEntity.ok(acceptedAllCharge);
    }
    
    @Operation(summary = "Status = false all Fienaku")
    @PostMapping("/rejectedAll")
    public ResponseEntity<List<charge>> rejectedAllCharge() {
        List<charge> rejectedAllCharge = serviceCharge.rejectedAll();
        return ResponseEntity.ok(rejectedAllCharge);
    }
    
    @Operation(summary = "Report Excel")
    @PostMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<charge> charges = serviceCharge.getAll();
        chargeExportExcel.export(charges, response);
    }
}
