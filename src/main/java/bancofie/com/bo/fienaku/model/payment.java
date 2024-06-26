package bancofie.com.bo.fienaku.model;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Charge ID", example = "1", type = "long")
    private Long id;

    @Schema(description = "Mount payment", example = "100.00", type = "double")
    private double mount_charge;

    @Schema(description = "collection Date", example = "12-11-2011", type = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Fienaku_Date", nullable = false, updatable = false)
    private Date collection_date;

    @Schema(description = "Qr Image", example = "localhost:8080/img/img.png", type = "String")
    private String qrAccount_image;

}
