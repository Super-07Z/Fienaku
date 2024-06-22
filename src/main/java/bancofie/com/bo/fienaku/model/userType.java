package bancofie.com.bo.fienaku.model;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;

public enum userType {

    @NotBlank
    @Schema(description = "UserType", example = "ROLE_ADMIN; ROLE_MANAGER; ROLE_USER", type = "ENUM")

    ROLE_ADMIN,
    ROLE_MANAGER,
    ROLE_USER;

}
