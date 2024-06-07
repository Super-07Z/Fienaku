package bancofie.com.bo.Fienaku.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    
    @Schema(description = "ID User", example = "1", type = "long")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Schema(description = "Name", example = "Juan", type = "String")
    private String name;    
        
    @NotBlank
    @Schema(description = "Last Name", example = "Perez", type = "String")
    private String last_name;
    
    @NotBlank
    @Schema(description = "Mail", example = "juan@email.com", type = "String")
    private String mail;
    
    @NotBlank
    @Schema(description = "Password", example = "153dsa123", type = "String")
    private String password;
    
    @NotBlank
    @Schema(description = "Account_Fie", example = "11515143", type = "String")
    private int Accoun_Fie;
    
    @Schema(description = "Creation Date", example ="11-11-2011", type = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_at", nullable = false, updatable = false)
    private Date createAt;
    
    @Schema(description = "Update Date", example ="12-11-2011", type = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_at")
    private Date updateAt;

    @PrePersist
    public void prePersist() {
        this.createAt = new Date();
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updateAt = new Date();
    }    
}
