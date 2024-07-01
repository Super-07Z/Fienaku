package bancofie.com.bo.fienaku.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class paymenthDTO {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Paymenth ID DTO", example = "1", type = "long")
    private Long chargeId;
    
    @Schema(description = "date pay", example = "11-11-2011", type = "Date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;
    
    @Schema(description = "Image", example = "localhost:8080/img/img.png", type = "String")
    private String image;
}

