package bancofie.com.bo.fienaku.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class fienakuDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "fienaku DTO ID", example = "1", type = "long")
    private long id;

    @Schema(description = "Name fienaku DTO", example = "Equipo azul", type = "String")
    private String name;

    @Schema(description = "User Image", example = "localhost:8080/img/img.png", type = "String")
    private String image;

    @Schema(description = "Code Fienaku", example = "3CD7O", type = "String")
    @Column(unique = true, length = 60)
    private String code;

    @Schema(description = "Mount Payment", example = "100.00 Bs.-", type = "double")
    private double mount;

    @Schema(description = "Penitence Fienaku", example = "10.00 Bs.-", type = "double")
    private double penitence;

    @Schema(description = "Payment Period", example = "15 days; 1 month, 2 month", type = "Integer")
    private Integer timespan;

    private String nombre;
    private String correo;

    public fienakuDTO(String nombre, String correo) {
        this.nombre = nombre;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
