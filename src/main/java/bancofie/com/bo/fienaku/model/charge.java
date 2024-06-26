package bancofie.com.bo.fienaku.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.*;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class charge implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Charge ID", example = "1", type = "long")
    private Long id;
        
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fienaku")
    @JsonIgnore
    private fienaku fienaku;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private user user;
    
    @Schema(description = "account", example = "111111", type = "double")
    private int account;
    
    @Schema(description = "Mount", example = "20 Bs.-", type = "double")
    private double mount;
    
    @Schema(description = "Date", example = "12-11-2011", type = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Date date;
    
    @Schema(description = "Image", example = "localhost:8080/img/img.png", type = "String")
    private String image;
    
    @Schema(description = "Status", example = "true", type = "boolean")
    private boolean status;
}
