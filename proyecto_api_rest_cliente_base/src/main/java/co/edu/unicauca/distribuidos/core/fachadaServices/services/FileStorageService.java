// Archivo: FileStorageService.java
package co.edu.unicauca.distribuidos.core.fachadaServices.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path root = Paths.get("C:\\Users\\Ana_Sofia\\OneDrive\\Documentos\\UNIVERSIDAD\\imagenesParcial");

    public FileStorageService() {
        try {
            // Crea el directorio si no existe
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo inicializar la carpeta para las subidas de archivos!", e);
        }
    }

    public String save(MultipartFile file) {
        try {
            // Generamos un nombre de archivo único para evitar que se sobreescriban
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String uniqueFilename = UUID.randomUUID().toString() + extension;

            // Copiamos el archivo a la carpeta de destino
            Files.copy(file.getInputStream(), this.root.resolve(uniqueFilename));

            // Devolvemos el nombre único del archivo guardado
            return uniqueFilename;
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el archivo: " + e.getMessage());
        }
    }
}