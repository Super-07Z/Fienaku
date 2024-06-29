package bancofie.com.bo.fienaku.model;

import java.util.*;
import javax.persistence.*;
import java.io.Serializable;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class charge implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Charge ID", example = "1", type = "long")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fienaku_id")
    @JsonIgnore
    private fienaku fienaku;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
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
