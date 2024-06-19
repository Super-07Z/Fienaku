package bancofie.com.bo.fienaku.controller;

import bancofie.com.bo.fienaku.error.apiError;
import bancofie.com.bo.fienaku.model.fienaku;
import bancofie.com.bo.fienaku.repository.fienakuRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class fienakuController {
    
private final fienakuRepository fienakuRepository;

    @Autowired
    public fienakuController(fienakuRepository fienakuRepository) {
        this.fienakuRepository = fienakuRepository;
    }
    
    @Operation(summary = "get list of all Fienakus")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "OK", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = fienaku.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = apiError.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = apiError.class)))
    })

    @GetMapping("/fienaku")
    public List<fienaku> getAllFienakus() {
        return fienakuRepository.findAll();
    }
    

}
