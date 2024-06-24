package bancofie.com.bo.fienaku.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class fienaku implements Serializable {

    @Schema(description = "Fienaku id", example = "1", type = "long")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Schema(description = "Fienaku Name", example = "Equipo Azul", type = "String")
    private String name;

    @Schema(description = "User Image", example = "localhost:8080/img/img.png", type = "String")
    private String image;

    @NotBlank
    @Schema(description = "Code Fienaku", example = "3CD7O", type = "String")
    @Column(unique = true, length = 60)
    private String code;

    @Schema(description = "Mount Payment", example = "100.00 Bs.-", type = "double")
    private double mount;

    @Schema(description = "Penitence Fienaku", example = "10.00 Bs.-", type = "double")
    private double penitence;

    @Schema(description = "Payment Period", example = "15 days; 1 month , 2 month", type = "Integer")
    private Integer timespan;

    @Schema(description = "Creation Date", example = "12-11-2011", type = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_At", nullable = false, updatable = false)
    private Date create;

    @Schema(description = "Update Date", example = "12-11-2011", type = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_At")
    private Date update;

    @JsonManagedReference
    @ManyToMany(mappedBy = "fienaku")
    private List<user> users = new ArrayList<>();

    @OneToMany(mappedBy = "fienaku", cascade = CascadeType.ALL)
    private List<payment> payment = new ArrayList<>();
    
    public void addUser(user data) {
        this.users.add(data);
        data.getFienaku().add(this);
    }

    public void removeUser(user data) {
        this.users.remove(data);
        data.getFienaku().remove(this);
    }

    @PrePersist
    public void prePersist() {
        this.create = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.update = new Date();
    }
}
