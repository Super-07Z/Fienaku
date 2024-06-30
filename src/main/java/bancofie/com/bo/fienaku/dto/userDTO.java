package bancofie.com.bo.fienaku.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class userDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "User ID DTO", example = "1", type = "long")
    private Long id;

    @Schema(description = "Last Name", example = "Juan", type = "String")
    private String username;
    
    @Schema(description = "Name", example = "Juan", type = "String")
    private String name;

    @Schema(description = "Last Name", example = "Perez", type = "String")
    private String lastname;
    
    @Schema(description = "job", example = "Technological innovation", type = "String")
    private String job;
    
    @Schema(description = "floor", example = "Piso 23", type = "String")
    private String floor;
    
    @Schema(description = "phone", example = "78426548", type = "String")
    private String phone;
    
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
