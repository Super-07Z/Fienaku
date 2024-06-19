package bancofie.com.bo.fienaku.dto;

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

public class fienakuDTO {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "fienaku DTO ID", example = "1", type = "long")
    private long id;
    
    @NotBlank
    @Schema(description = "Name fienaku DTO", example = "Equipo azul", type = "String")    
    private String name;
    
    @NotBlank
    @Schema(description = "Code Fienaku", example = "3CD7O", type="String")
    @Column(unique=true, length = 60)
    private String code;
    
    @NotBlank
    @Schema(description = "Mount Payment", example = "100.00 Bs.-", type = "double")
    private double mount;
        
    @NotBlank
    @Schema(description = "Penitence Fienaku", example = "10.00 Bs.-", type = "double")
    private double penitence;
    
    @Schema(description = "Payment Period", example = "15 days; 1 month , 2 month", type = "Integer")
    private Integer timespan; 
    
    @NotBlank
    @Schema(description = "Manager Name", example = "Juan Perez (MANAGER)", type = "String")    
    private String manager; 
    
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
