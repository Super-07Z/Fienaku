package bancofie.com.bo.Fienaku.dto;

import bancofie.com.bo.Fienaku.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDTO {    
    
    @Schema(description = "User DTO", example = "1", type = "long")
    private long id;
    
    @NotBlank
    @Schema(description = "Name", example = "juan", type = "String")
    private String name;
    
    @NotBlank
    @Schema(description = "Last Name", example = "perez", type = "String")
    private String lastname;
        
    @NotBlank
    @Schema(description = "User - Fienaku", example = "Fienaku - Piso 23", type = "String")
    private String fienaku;

    @Schema(description = "User Image", example = "localhost:8080/img/img.png", type = "String")
    private String image;
    
    @Schema(description = "User Typeer", example = "user ; admin ; manager", type = "Enum")
    @Enumerated(EnumType.STRING)
    private User.UserType user_type = User.UserType.USER;

    @Schema(description = "Status", example = "active ; suspended", type = "Enum")
    @Enumerated(EnumType.STRING)
    private User.Status status = User.Status.ACTIVE;
    
    @Schema(description = "Creation Date", example ="11-11-2011", type = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Schema(description = "Update Date", example ="12-11-2011", type = "Date")
    private Date updatedAt;
    
    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Date();
    }

    public enum UserType {
        USER,
        ADMIN,
        MANAGER
    }

    public enum Status {
        ACTIVE,
        SUSPENDED
    }    
}