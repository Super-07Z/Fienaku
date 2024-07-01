package bancofie.com.bo.fienaku.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class paymenth implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Payment ID", example = "1", type = "long")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "charge_id")
    @Schema(description = "Charge associated payment")
    @JsonIgnore
    private charge charge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Schema(description = "User associated payment")
    @JsonIgnore
    private user user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fienaku_id")
    @Schema(description = "Fienaku associated payment")
    @JsonIgnore
    private fienaku fienaku;

    @Schema(description = "Image", example = "localhost:8080/img/img.png", type = "String")
    private String image;
        
    @Schema(description = "Payment Amount", example = "100.00 Bs.-", type = "double")
    private double amount;

    @Schema(description = "Payment Date", example = "12-11-2011", type = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Date date;

    @Schema(description = "Payment Status", example = "true", type = "boolean")
    private boolean status;
}
