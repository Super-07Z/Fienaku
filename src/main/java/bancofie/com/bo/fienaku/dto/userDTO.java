package bancofie.com.bo.fienaku.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class userDTO {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "User ID DTO", example = "1", type = "long")
    private Long id;

    @NotBlank
    @Schema(description = "Name", example = "Juan", type = "String")    
    private String name;

    @NotBlank
    @Schema(description = "Last Name", example = "Perez", type = "String")
    private String lastname;

    @NotBlank
    @Schema(description = "Mail", example = "juan@email.com", type = "String")
    @Column(unique=true, length = 60)
    private String mail;

    @NotBlank
    @Schema(description = "Password", example = "123", type = "String")
    private String password;

    @Schema(description = "Account", example = "11515143", type = "Integer")
    @Column(unique=true, length = 60)
    private int account;

    @Schema(description = "User Image", example = "localhost:8080/img/img.png", type = "String")
    private String image;
    
    @Schema(description = "Creation Date", example = "11-11-2011", type = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_At", nullable = false, updatable = false)
    private Date create;

    @Schema(description = "Update Date", example = "12-11-2011", type = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_At")
    private Date update;
    
    @PrePersist
    public void prePersist() {
        this.create = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.update = new Date();
    }
}
