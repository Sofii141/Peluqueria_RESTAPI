package co.edu.unicauca.distribuidos.core.fachadaServices.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicioDTOPeticion {
    private String nombre;
    private String descripcion;
    private Double precio;
    private String imagen;
    private Boolean disponible;
    private CategoriaDTOPeticion objCategoria;
}