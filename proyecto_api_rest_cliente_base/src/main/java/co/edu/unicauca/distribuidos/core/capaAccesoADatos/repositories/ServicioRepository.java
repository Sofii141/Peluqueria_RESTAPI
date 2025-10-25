package co.edu.unicauca.distribuidos.core.capaAccesoADatos.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.CategoriaEntity;
import co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.ServicioEntity; // CAMBIO
import co.edu.unicauca.distribuidos.core.capaAccesoADatos.repositories.conexion.ConexionBD;

@Repository
public class ServicioRepository {
    private final ConexionBD conexionABaseDeDatos;

    public ServicioRepository() {
        conexionABaseDeDatos = new ConexionBD();
    }

    public Optional<Collection<ServicioEntity>> findByCategoriaId(Integer idCategoria) {
        System.out.println("Listando servicios por categoría desde la base de datos");
        Collection<ServicioEntity> servicios = new LinkedList<>();

        conexionABaseDeDatos.conectar();
        try {
            PreparedStatement sentencia = null;
            String consulta = "select * from servicios s join categorias c on s.idCategoria=c.id where s.idCategoria=?";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);
            sentencia.setInt(1, idCategoria);
            ResultSet res = sentencia.executeQuery();
            while (res.next()) {
                ServicioEntity objServicio = new ServicioEntity();
                objServicio.setId(res.getInt("id"));
                objServicio.setNombre(res.getString("nombre"));
                objServicio.setDescripcion(res.getString("descripcion"));
                objServicio.setPrecio(res.getDouble("precio"));
                objServicio.setImagen(res.getString("imagen"));
                objServicio.setFechaCreacion(res.getDate("fechaCreacion"));
                objServicio.setDisponible(res.getBoolean("disponible"));
                objServicio.setObjCategoria(new CategoriaEntity(res.getInt("idCategoria"), res.getString("nombreCategoria")));
                servicios.add(objServicio);
            }
            sentencia.close();
            conexionABaseDeDatos.desconectar();
        } catch (SQLException e) {
            System.out.println("Error en la consulta por categoría: " + e.getMessage());
        }
        return servicios.isEmpty() ? Optional.empty() : Optional.of(servicios);
    }

    public ServicioEntity save(ServicioEntity objServicio) {
        System.out.println("Registrando servicio en base de datos");
        ServicioEntity objServicioAlmacenado = null;
        int resultado = -1;

        try {
            conexionABaseDeDatos.conectar();
            PreparedStatement sentencia = null;
            String consulta = "insert into servicios(nombre, descripcion, precio, imagen, fechaCreacion, disponible, idCategoria) values(?,?,?,?,?,?,?)";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);

            sentencia.setString(1, objServicio.getNombre());
            sentencia.setString(2, objServicio.getDescripcion());
            sentencia.setDouble(3, objServicio.getPrecio());
            sentencia.setString(4, objServicio.getImagen());
            sentencia.setDate(5, new java.sql.Date(objServicio.getFechaCreacion().getTime()));
            sentencia.setBoolean(6, objServicio.getDisponible());
            sentencia.setInt(7, objServicio.getObjCategoria().getId());

            resultado = sentencia.executeUpdate();

            ResultSet generatedKeys = sentencia.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idGenerado = generatedKeys.getInt(1);
                objServicio.setId(idGenerado);
                if (resultado == 1) {
                    objServicioAlmacenado = this.findById(idGenerado).get();
                }
            }
            generatedKeys.close();
            sentencia.close();
            conexionABaseDeDatos.desconectar();
        } catch (SQLException e) {
            System.out.println("Error en la inserción: " + e.getMessage());
        }
        return objServicioAlmacenado;
    }

    public Optional<Collection<ServicioEntity>> findAll() {
        System.out.println("Listando servicios de base de datos");
        Collection<ServicioEntity> servicios = new LinkedList<>();

        conexionABaseDeDatos.conectar();
        try {
            PreparedStatement sentencia = null;
            String consulta = "select * from servicios s join categorias c on s.idCategoria = c.id";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);
            ResultSet res = sentencia.executeQuery();
            while (res.next()) {
                ServicioEntity objServicio = new ServicioEntity();
                objServicio.setId(res.getInt("id"));
                objServicio.setNombre(res.getString("nombre"));
                objServicio.setDescripcion(res.getString("descripcion"));
                objServicio.setPrecio(res.getDouble("precio"));
                objServicio.setImagen(res.getString("imagen"));
                objServicio.setFechaCreacion(res.getDate("fechaCreacion"));
                objServicio.setDisponible(res.getBoolean("disponible"));
                objServicio.setObjCategoria(new CategoriaEntity(res.getInt("idCategoria"), res.getString("nombreCategoria")));
                servicios.add(objServicio);
            }
            sentencia.close();
            conexionABaseDeDatos.desconectar();
        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        }
        return servicios.isEmpty() ? Optional.empty() : Optional.of(servicios);
    }

    public Optional<ServicioEntity> findById(Integer idServicio) {
        System.out.println("Consultar servicio de base de datos");
        ServicioEntity objServicio = null;

        conexionABaseDeDatos.conectar();
        try {
            PreparedStatement sentencia = null;
            String consulta = "select * from servicios s join categorias c on s.idCategoria=c.id where s.id=?";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);
            sentencia.setInt(1, idServicio);
            ResultSet res = sentencia.executeQuery();
            while (res.next()) {
                objServicio = new ServicioEntity();
                objServicio.setId(res.getInt("id"));
                objServicio.setNombre(res.getString("nombre"));
                objServicio.setDescripcion(res.getString("descripcion"));
                objServicio.setPrecio(res.getDouble("precio"));
                objServicio.setImagen(res.getString("imagen"));
                objServicio.setFechaCreacion(res.getDate("fechaCreacion"));
                objServicio.setDisponible(res.getBoolean("disponible"));
                objServicio.setObjCategoria(new CategoriaEntity(res.getInt("idCategoria"), res.getString("nombreCategoria")));
            }
            sentencia.close();
            conexionABaseDeDatos.desconectar();
        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        }
        return objServicio == null ? Optional.empty() : Optional.of(objServicio);
    }

    public Optional<ServicioEntity> update(Integer idServicio, ServicioEntity objServicio) {
        System.out.println("Actualizar servicio de base de datos");
        ServicioEntity objServicioActualizado = null;
        conexionABaseDeDatos.conectar();
        int resultado = -1;
        try {
            PreparedStatement sentencia = null;
            String consulta = "update servicios set nombre=?, descripcion=?, precio=?, imagen=?, fechaCreacion=?, disponible=?, idCategoria=? where id=?";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);

            sentencia.setString(1, objServicio.getNombre());
            sentencia.setString(2, objServicio.getDescripcion());
            sentencia.setDouble(3, objServicio.getPrecio());
            sentencia.setString(4, objServicio.getImagen());
            sentencia.setDate(5, new java.sql.Date(objServicio.getFechaCreacion().getTime()));
            sentencia.setBoolean(6, objServicio.getDisponible());
            sentencia.setInt(7, objServicio.getObjCategoria().getId());
            sentencia.setInt(8, idServicio);

            resultado = sentencia.executeUpdate();
            sentencia.close();
            conexionABaseDeDatos.desconectar();
        } catch (SQLException e) {
            System.out.println("Error en la actualización: " + e.getMessage());
        }

        if (resultado == 1) {
            objServicioActualizado = this.findById(idServicio).get();
        }
        return objServicioActualizado == null ? Optional.empty() : Optional.of(objServicioActualizado);
    }

    public boolean delete(Integer idServicio) {
        System.out.println("Eliminar servicio de base de datos");
        conexionABaseDeDatos.conectar();
        int resultado = -1;
        try {
            PreparedStatement sentencia = null;
            String consulta = "delete from servicios where id=?";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);
            sentencia.setInt(1, idServicio);
            resultado = sentencia.executeUpdate();
            sentencia.close();
            conexionABaseDeDatos.desconectar();
        } catch (SQLException e) {
            System.out.println("Error en la eliminación: " + e.getMessage());
        }
        return resultado == 1;
    }
}