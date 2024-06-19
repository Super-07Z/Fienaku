package bancofie.com.bo.fienaku.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class charge implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Charge ID", example = "1", type="long")
    private Long id;
    
    @Schema(description = "Mount charge", example = "100.00", type="double")
    private double mount_charge;
    
    @Schema(description = "collection Date", example = "12-11-2011", type = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Fienaku_Date", nullable = false, updatable = false)
    private Date collection_date;
    
    @Schema(description = "Qr Image", example = "localhost:8080/img/img.png", type = "String")
    private String qrAccount_image;
    
}
