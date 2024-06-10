/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bancofie.com.bo.Fienaku.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author david.ilario
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Fienaku implements Serializable {
    
    @Schema(description = "Fienaku id", example = "1", type="long")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Schema(description = "Fienaku Name", example = "Grupo1", type="String")
    private String name;
    
    @NotBlank
    @Positive
    @Schema(description = "Participants", example = "10", type = "long")
    private long participants;
    
    
    @Schema(description = "Code Fienaku", example = "000000", type="int[]")
    private int[] codeFienaku = new int[6];
    
    @Schema (description = "Game time", example = "15 dias ; 1Mes ; 2Meses",type="Enum")
    @Enumerated(EnumType.STRING)
    private GameTime gametime = GameTime.QuinceDias;
    
    @NotBlank
    @Positive
    @Schema(description = "Mount game", example ="100.00",type="double")
    private double Mount_game;
    
    
    
    public enum GameTime{
        QuinceDias,
        UnMes,
        DosMeses,
    }
    
}
