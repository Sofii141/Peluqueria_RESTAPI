package co.edu.unicauca.distribuidos.core.capaAccesoADatos.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicioEntity {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private String imagen;
    private Date fechaCreacion;
    private Boolean disponible;
    private CategoriaEntity objCategoria;
}