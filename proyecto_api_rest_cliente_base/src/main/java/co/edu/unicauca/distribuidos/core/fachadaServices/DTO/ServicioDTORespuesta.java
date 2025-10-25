package co.edu.unicauca.distribuidos.core.fachadaServices.DTO;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicioDTORespuesta {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private String imagen;
    private Date fechaCreacion;
    private Boolean disponible;
    private CategoriaDTORespuesta objCategoria;
}