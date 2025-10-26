package co.edu.unicauca.distribuidos.core.capaControladores;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ServicioDTOPeticion; // CAMBIO
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ServicioDTORespuesta; // CAMBIO
import co.edu.unicauca.distribuidos.core.fachadaServices.services.IServicioService; // CAMBIO
import org.springframework.web.multipart.MultipartFile;

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

	@PutMapping("/servicios/{id}")
	public ServicioDTORespuesta actualizarServicio(@RequestBody ServicioDTOPeticion servicio, @PathVariable Integer id) {
		return servicioService.update(id, servicio);
	}

	@DeleteMapping("/servicios/{id}")
	public Boolean eliminarServicio(@PathVariable Integer id) {
		return servicioService.delete(id);
	}

	@PostMapping(value = "/servicios", consumes = "application/json")
	public ResponseEntity<ServicioDTORespuesta> crearServicio(@RequestBody ServicioDTOPeticion servicio) {
		ServicioDTORespuesta nuevoServicio = servicioService.save(servicio);
		return new ResponseEntity<>(nuevoServicio, HttpStatus.CREATED);
	}


	@PostMapping(value = "/servicios/con-imagen", consumes = "multipart/form-data")
	public ResponseEntity<ServicioDTORespuesta> crearServicioConImagen(
			@RequestParam("imagen") MultipartFile imagenFile,
			@RequestParam("servicio") String servicioJson) {
		try {
			ServicioDTORespuesta nuevoServicio = servicioService.saveWithImage(imagenFile, servicioJson);
			return new ResponseEntity<>(nuevoServicio, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}