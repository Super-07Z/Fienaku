package bancofie.com.bo.fienaku.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class userDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "User ID DTO", example = "1", type = "long")
    private Long id;

    @Schema(description = "Name", example = "Juan", type = "String")
    private String name;

    @Schema(description = "Last Name", example = "Perez", type = "String")
    private String lastname;

    @Schema(description = "Mail", example = "juan@email.com", type = "String")
    @Column(unique = true, length = 60)
    private String mail;

    @Schema(description = "Password", example = "123", type = "String")
    private String password;

    @Schema(description = "Account", example = "11515143", type = "Integer")
    @Column(unique = true, length = 60)
    private int account;

    @Schema(description = "User Image", example = "localhost:8080/img/img.png", type = "String")
    private String image;
}
