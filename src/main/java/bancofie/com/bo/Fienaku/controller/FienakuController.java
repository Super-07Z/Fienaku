/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bancofie.com.bo.Fienaku.controller;

import bancofie.com.bo.Fienaku.error.ApiError;
import bancofie.com.bo.Fienaku.model.Fienaku;
import bancofie.com.bo.Fienaku.model.FienakuRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author david.ilario
 */
@RestController
@RequiredArgsConstructor
public class FienakuController {
private final FienakuRepository fienakuRepository;

    @Operation(summary = "get list of all Fienakus")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "OK", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Fienaku.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ApiError.class)))
    })

    @PostMapping("/fienaku")
    public List<Fienaku> getAll() {
            return fienakuRepository.findAll();
    }
}
