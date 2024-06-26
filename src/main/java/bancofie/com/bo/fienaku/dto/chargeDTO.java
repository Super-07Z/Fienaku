package bancofie.com.bo.fienaku.dto;

import bancofie.com.bo.fienaku.model.charge;
import java.util.*;
import javax.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class chargeDTO {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "payment DTO ID", example = "1", type = "long")
    private Long id;
    
    @Schema(description = "name", example = "Juan", type = "String")
    private String name;

    @Schema(description = "last Name", example = "Perez", type = "String")
    private String lastName;
    
    @Schema(description = "account", example = "11515143", type = "Integer")
    @Column(unique = true, length = 60)
    private int account;
          
    @Schema(description = "mount", example = "200 Bs.-", type = "double")
    private double mount;

    @Schema(description = "Image", example = "localhost:8080/img/img.png", type = "String")
    private String image;
    
    @Schema(description = "status", example = "true / false", type = "boolean")
    private boolean status;
    
    @Schema(description = "date pay", example = "11-11-2011", type = "Date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;
    
    public static chargeDTO charge(charge data) {
        return new chargeDTO(data.getId(), data.getUser().getName(), data.getUser().getLastname(), data.getUser().getAccount(), data.getMount(), data.getImage(), data.isStatus(), data.getDate());
    }
}
