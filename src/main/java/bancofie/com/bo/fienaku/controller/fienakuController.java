package bancofie.com.bo.fienaku.controller;

import bancofie.com.bo.fienaku.dto.fienakuDTO;
import bancofie.com.bo.fienaku.error.apiError;
import bancofie.com.bo.fienaku.model.fienaku;
import bancofie.com.bo.fienaku.model.user;
import bancofie.com.bo.fienaku.model.userType;
import bancofie.com.bo.fienaku.repository.fienakuRepository;
import bancofie.com.bo.fienaku.repository.userRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/fienaku")
@RestController
public class fienakuController {
    
    @Autowired
    private final fienakuRepository fienaxRepository;

    @Autowired
    private final userRepository usersRepository;

    public fienakuController(fienakuRepository fienakuRepository, userRepository usersRepository) {
        this.fienaxRepository = fienakuRepository;
        this.usersRepository = usersRepository;
    }
    
    
    @Operation(summary = "get list of all Fienakus")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "OK", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = fienaku.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = apiError.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = apiError.class)))
    })

    @PostMapping()
    public List<fienaku> getAllFienakus() {
        return fienaxRepository.findAll();
    }
    
    @PostMapping("/create")
    public fienaku createFienakuDTO(@RequestBody fienakuDTO fienakudto) {
        fienaku fienax = new fienaku();
        fienax.setName(fienakudto.getName());
        fienax.setCode(fienakudto.getCode());
        fienax.setMount(fienakudto.getMount());
        fienax.setPenitence(fienakudto.getPenitence());
        fienax.setTimespan(fienakudto.getTimespan());
        fienax.setCreate(fienakudto.getCreate());
        fienax.setUpdate(fienakudto.getUpdate());

        fienaku fienakusave = fienaxRepository.save(fienax);

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        user userManager = usersRepository.findByMail(username);

        userManager.setUsertype(userType.ROLE_MANAGER);
        usersRepository.save(userManager);

        return fienakusave;
    }


}
