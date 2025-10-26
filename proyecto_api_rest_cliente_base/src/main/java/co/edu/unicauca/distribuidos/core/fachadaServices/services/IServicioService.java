package co.edu.unicauca.distribuidos.core.fachadaServices.services;

import java.util.List;
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ServicioDTOPeticion;
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ServicioDTORespuesta;
import org.springframework.web.multipart.MultipartFile;

public interface IServicioService {
	public List<ServicioDTORespuesta> findAll();
	public ServicioDTORespuesta findById(Integer id);
	public ServicioDTORespuesta save(ServicioDTOPeticion servicio);
	public ServicioDTORespuesta update(Integer id, ServicioDTOPeticion servicio);
	public boolean delete(Integer id);
	public List<ServicioDTORespuesta> findByCategoriaId(Integer idCategoria);
	public ServicioDTORespuesta saveWithImage(MultipartFile imagenFile, String servicioJson);
}