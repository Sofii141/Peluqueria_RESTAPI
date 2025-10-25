package co.edu.unicauca.distribuidos.core.capaControladores;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ServicioDTOPeticion; // CAMBIO
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ServicioDTORespuesta; // CAMBIO
import co.edu.unicauca.distribuidos.core.fachadaServices.services.IServicioService; // CAMBIO

@RestController
@RequestMapping("/api")

@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ServicioRestController {

	@GetMapping("/servicios/categoria/{id}")
	public List<ServicioDTORespuesta> listarServiciosPorCategoria(@PathVariable Integer id) {
		return servicioService.findByCategoriaId(id);
	}

	@Autowired
	private IServicioService servicioService;

	@GetMapping("/servicios")
	public List<ServicioDTORespuesta> listarServicios() {
		return servicioService.findAll();
	}

	@GetMapping("/servicios/{id}")
	public ServicioDTORespuesta consultarServicio(@PathVariable Integer id) {
		return servicioService.findById(id);
	}

	@PostMapping("/servicios")
	public ServicioDTORespuesta crearServicio(@RequestBody ServicioDTOPeticion servicio) {
		return servicioService.save(servicio);
	}

	@PutMapping("/servicios/{id}")
	public ServicioDTORespuesta actualizarServicio(@RequestBody ServicioDTOPeticion servicio, @PathVariable Integer id) { // CAMBIO
		return servicioService.update(id, servicio);
	}

	@DeleteMapping("/servicios/{id}")
	public Boolean eliminarServicio(@PathVariable Integer id) {
		return servicioService.delete(id);
	}
}