package co.edu.unicauca.distribuidos.core.fachadaServices.services;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.ServicioEntity;
import co.edu.unicauca.distribuidos.core.capaAccesoADatos.repositories.ServicioRepository;
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ServicioDTOPeticion;
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ServicioDTORespuesta;

@Service
public class ServicioServiceImpl implements IServicioService {
	private final ServicioRepository servicioRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public ServicioServiceImpl(ServicioRepository servicioRepository, ModelMapper modelMapper) {
		this.servicioRepository = servicioRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public List<ServicioDTORespuesta> findAll() {
		Optional<Collection<ServicioEntity>> serviciosEntityOpt = this.servicioRepository.findAll();

		if (serviciosEntityOpt.isEmpty()) {
			return List.of();
		}

		Collection<ServicioEntity> serviciosEntity = serviciosEntityOpt.get();
		return this.modelMapper.map(serviciosEntity, new TypeToken<List<ServicioDTORespuesta>>() {}.getType());
	}

	@Override
	public ServicioDTORespuesta findById(Integer id) {
		ServicioDTORespuesta servicioRetornar = null;
		Optional<ServicioEntity> optionalServicio = this.servicioRepository.findById(id);

		if(optionalServicio.isPresent()) {
			ServicioEntity servicioEntity = optionalServicio.get();
			servicioRetornar = this.modelMapper.map(servicioEntity, ServicioDTORespuesta.class);
		}

		return servicioRetornar;
	}

	@Override
	public ServicioDTORespuesta save(ServicioDTOPeticion servicio) {
		ServicioEntity servicioEntity = this.modelMapper.map(servicio, ServicioEntity.class);

		// Asignamos la fecha de creación del lado del servidor para asegurar consistencia
		servicioEntity.setFechaCreacion(new Date());

		// Guardamos la nueva entidad en la base de datos
		ServicioEntity objServicioEntity = this.servicioRepository.save(servicioEntity);

		// Mapeamos la entidad guardada (con su nuevo ID) a un DTO de respuesta
		return this.modelMapper.map(objServicioEntity, ServicioDTORespuesta.class);
	}

	@Override
	public ServicioDTORespuesta update(Integer id, ServicioDTOPeticion servicioNuevosDatos) {
		ServicioEntity servicioActualizado = null;
		Optional<ServicioEntity> servicioEntityOp = this.servicioRepository.findById(id);

		if(servicioEntityOp.isPresent()) {
			// Obtenemos la entidad existente
			ServicioEntity servicioExistente = servicioEntityOp.get();

			// Actualizamos los campos con los datos del DTO
			servicioExistente.setNombre(servicioNuevosDatos.getNombre());
			servicioExistente.setDescripcion(servicioNuevosDatos.getDescripcion());
			servicioExistente.setPrecio(servicioNuevosDatos.getPrecio());
			servicioExistente.setImagen(servicioNuevosDatos.getImagen());
			servicioExistente.setDisponible(servicioNuevosDatos.getDisponible());
			servicioExistente.getObjCategoria().setId(servicioNuevosDatos.getObjCategoria().getId());

			// Llamamos al método update del repositorio
			Optional<ServicioEntity> optionalServicio = this.servicioRepository.update(id, servicioExistente);
			if(optionalServicio.isPresent()){
				servicioActualizado = optionalServicio.get();
			}
		}

		// Mapeamos la entidad actualizada al DTO de respuesta
		return this.modelMapper.map(servicioActualizado, ServicioDTORespuesta.class);
	}

	@Override
	public boolean delete(Integer id) {
		return this.servicioRepository.delete(id);
	}

	@Override
	public List<ServicioDTORespuesta> findByCategoriaId(Integer idCategoria) {
		Optional<Collection<ServicioEntity>> serviciosEntityOpt = this.servicioRepository.findByCategoriaId(idCategoria);

		if (serviciosEntityOpt.isEmpty()) {
			return List.of();
		}

		Collection<ServicioEntity> serviciosEntity = serviciosEntityOpt.get();
		return this.modelMapper.map(serviciosEntity, new TypeToken<List<ServicioDTORespuesta>>() {}.getType());
	}
}