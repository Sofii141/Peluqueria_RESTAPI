package co.edu.unicauca.distribuidos.core.capaAccesoADatos.models;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaEntity {
	private Integer id;
	private String nombre;
}