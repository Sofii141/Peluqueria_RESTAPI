package co.edu.unicauca.distribuidos.core.fachadaServices.services;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.ServicioEntity;
import co.edu.unicauca.distribuidos.core.capaAccesoADatos.repositories.ServicioRepository;
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ServicioDTOPeticion;
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ServicioDTORespuesta;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ServicioServiceImpl implements IServicioService {
	private final ServicioRepository servicioRepository;
	private final ModelMapper modelMapper;
	private final FileStorageService storageService;
	private final ObjectMapper objectMapper;

	@Autowired
	public ServicioServiceImpl(ServicioRepository servicioRepository,
							   ModelMapper modelMapper,
							   FileStorageService storageService,
							   ObjectMapper objectMapper) {
		this.servicioRepository = servicioRepository;
		this.modelMapper = modelMapper;
		this.storageService = storageService;
		this.objectMapper = objectMapper;
	}

	@Override
	public ServicioDTORespuesta updateWithImage(Integer id, MultipartFile imagenFile, String servicioJson) {
		try {
			// Paso A: Convertir el texto JSON del servicio a un objeto DTO
			ServicioDTOPeticion servicioDTO = objectMapper.readValue(servicioJson, ServicioDTOPeticion.class);

			// Paso B: Guardar la nueva imagen y actualizar la URL en el DTO
			// Solo si se proporciona un nuevo archivo de imagen.
			if (imagenFile != null && !imagenFile.isEmpty()) {
				String nombreImagen = storageService.save(imagenFile);
				String imageUrl = "http://localhost:5000/uploads/images/" + nombreImagen;
				servicioDTO.setImagen(imageUrl);
			}

			// Paso C: Llamar al método 'update' existente para reutilizar la lógica de actualización
			return this.update(id, servicioDTO);

		} catch (Exception e) {
			// Lanzar una excepción para que el controlador la pueda capturar
			throw new RuntimeException("Fallo al procesar la actualización del servicio con imagen", e);
		}
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
		servicioEntity.setFechaCreacion(new Date());
		ServicioEntity objServicioEntity = this.servicioRepository.save(servicioEntity);
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

	@Override
	public ServicioDTORespuesta saveWithImage(MultipartFile imagenFile, String servicioJson) {
		try {
			// Paso A: Guardar la imagen en el disco y obtener su nombre único
			String nombreImagen = storageService.save(imagenFile);

			// Paso B: Construir la URL pública completa para la imagen
			String imageUrl = "http://localhost:5000/uploads/images/" + nombreImagen;

			// Paso C: Convertir el texto JSON del servicio a un objeto DTO
			ServicioDTOPeticion servicioDTO = objectMapper.readValue(servicioJson, ServicioDTOPeticion.class);

			// Paso D: Asignar la URL de la imagen al objeto del servicio
			servicioDTO.setImagen(imageUrl);

			// Paso E: Llamar al método save existente para reutilizar la lógica de guardado
			return this.save(servicioDTO);

		} catch (Exception e) {
			// Lanzar una excepción de runtime para que el controlador la pueda capturar
			throw new RuntimeException("Fallo al procesar el servicio con imagen", e);
		}
	}
}