package ar.edu.davinci.dao;

import ar.edu.davinci.modelo.entidades.Adopcion;
import ar.edu.davinci.modelo.entidades.Mascota;
import ar.edu.davinci.modelo.entidades.Adoptante;
import ar.edu.davinci.modelo.entidades.Empleado;
import ar.edu.davinci.modelo.entidades.Gato;
import ar.edu.davinci.modelo.entidades.Perro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdopcionDAO extends BaseDAO {

    public int crear(Adopcion adopcion) throws SQLException {
        String sql = "INSERT INTO adopciones (id_mascota, id_adoptante, id_empleado, observaciones) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, adopcion.getMascota().getIdMascota());
            stmt.setInt(2, adopcion.getAdoptante().getIdAdoptante());
            stmt.setInt(3, adopcion.getEmpleado().getIdEmpleado());
            stmt.setString(4, "Adopcion procesada correctamente");

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Error al crear adopcion");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int numeroAdopcion = generatedKeys.getInt(1);
                    adopcion.setNumeroAdopcion(numeroAdopcion);
                    return numeroAdopcion;
                }
            }
        }
        return 0;
    }

    public Adopcion buscarPorId(int numeroAdopcion) throws SQLException {
        String sql = """
        SELECT 
            a.numero_adopcion, a.fecha_adopcion, a.observaciones,
            m.id_mascota, m.nombre as mascota_nombre, m.especie, m.raza, 
            m.fecha_nacimiento, m.peso, m.temperatura, m.estado as mascota_estado, m.disponible,
            ad.id_adoptante, ad.nombre as adoptante_nombre, ad.apellido as adoptante_apellido, 
            ad.direccion as adoptante_direccion, ad.edad, ad.telefono, ad.email,
            e.id_empleado, e.nombre as empleado_nombre, e.apellido as empleado_apellido, 
            e.direccion as empleado_direccion, e.username, e.password, e.email as empleado_email
        FROM adopciones a
        JOIN mascotas m ON a.id_mascota = m.id_mascota
        JOIN adoptantes ad ON a.id_adoptante = ad.id_adoptante  
        JOIN empleados e ON a.id_empleado = e.id_empleado
        WHERE a.numero_adopcion = ?
        """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, numeroAdopcion);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Mascota mascota;
                String especie = rs.getString("especie");
                if ("Gato".equalsIgnoreCase(especie)) {
                    mascota = new Gato(
                            rs.getInt("id_mascota"),
                            rs.getString("mascota_nombre"),
                            rs.getString("fecha_nacimiento"),
                            rs.getDouble("peso"),
                            rs.getString("raza"),
                            rs.getDouble("temperatura"),
                            rs.getString("mascota_estado")
                    );
                } else if ("Perro".equalsIgnoreCase(especie)) {
                    mascota = new Perro(
                            rs.getInt("id_mascota"),
                            rs.getString("mascota_nombre"),
                            rs.getString("fecha_nacimiento"),
                            rs.getDouble("peso"),
                            rs.getString("raza"),
                            rs.getDouble("temperatura"),
                            rs.getString("mascota_estado")
                    );
                } else {
                    throw new SQLException("Especie de mascota no reconocida: " + especie);
                }

                Adoptante adoptante = new Adoptante(
                        rs.getInt("id_adoptante"),
                        rs.getString("adoptante_nombre"),
                        rs.getString("adoptante_apellido"),
                        rs.getString("adoptante_direccion"),
                        rs.getInt("edad"),
                        rs.getString("telefono"),
                        rs.getString("email")
                );

                Empleado empleado = new Empleado(
                        rs.getInt("id_empleado"),
                        rs.getString("empleado_nombre"),
                        rs.getString("empleado_apellido"),
                        rs.getString("empleado_direccion"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("empleado_email")
                );

                Adopcion adopcion = new Adopcion(mascota, adoptante, empleado);
                adopcion.setNumeroAdopcion(rs.getInt("numero_adopcion"));
                adopcion.setFecha(rs.getTimestamp("fecha_adopcion").toString());

                return adopcion;
            }
        }
        return null;
    }

    public List<Adopcion> obtenerTodas() throws SQLException {
        List<Adopcion> adopciones = new ArrayList<>();
        String sql = """
        SELECT 
            a.numero_adopcion, a.fecha_adopcion, a.observaciones,
            m.id_mascota, m.nombre as mascota_nombre, m.especie, m.raza, 
            m.fecha_nacimiento, m.peso, m.temperatura, m.estado as mascota_estado, m.disponible,
            ad.id_adoptante, ad.nombre as adoptante_nombre, ad.apellido as adoptante_apellido, 
            ad.direccion as adoptante_direccion, ad.edad, ad.telefono, ad.email,
            e.id_empleado, e.nombre as empleado_nombre, e.apellido as empleado_apellido, 
            e.direccion as empleado_direccion, e.username, e.password, e.email as empleado_email
        FROM adopciones a
        JOIN mascotas m ON a.id_mascota = m.id_mascota
        JOIN adoptantes ad ON a.id_adoptante = ad.id_adoptante  
        JOIN empleados e ON a.id_empleado = e.id_empleado
        ORDER BY a.fecha_adopcion DESC
        """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Mascota mascota;
                String especie = rs.getString("especie");
                if ("Gato".equalsIgnoreCase(especie)) {
                    mascota = new Gato(
                            rs.getInt("id_mascota"),
                            rs.getString("mascota_nombre"),
                            rs.getString("fecha_nacimiento"),
                            rs.getDouble("peso"),
                            rs.getString("raza"),
                            rs.getDouble("temperatura"),
                            rs.getString("mascota_estado")
                    );
                } else if ("Perro".equalsIgnoreCase(especie)) {
                    mascota = new Perro(
                            rs.getInt("id_mascota"),
                            rs.getString("mascota_nombre"),
                            rs.getString("fecha_nacimiento"),
                            rs.getDouble("peso"),
                            rs.getString("raza"),
                            rs.getDouble("temperatura"),
                            rs.getString("mascota_estado")
                    );
                } else {
                    throw new SQLException("Especie de mascota no reconocida: " + especie);
                }

                Adoptante adoptante = new Adoptante(
                        rs.getInt("id_adoptante"),
                        rs.getString("adoptante_nombre"),
                        rs.getString("adoptante_apellido"),
                        rs.getString("adoptante_direccion"),
                        rs.getInt("edad"),
                        rs.getString("telefono"),
                        rs.getString("email")
                );

                Empleado empleado = new Empleado(
                        rs.getInt("id_empleado"),
                        rs.getString("empleado_nombre"),
                        rs.getString("empleado_apellido"),
                        rs.getString("empleado_direccion"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("empleado_email")
                );

                Adopcion adopcion = new Adopcion(mascota, adoptante, empleado);
                adopcion.setNumeroAdopcion(rs.getInt("numero_adopcion"));
                adopcion.setFecha(rs.getTimestamp("fecha_adopcion").toString());

                adopciones.add(adopcion);
            }
        }
        return adopciones;
    }

    public List<Adopcion> obtenerPorEmpleado(int idEmpleado) throws SQLException {
        List<Adopcion> adopciones = new ArrayList<>();
        String sql = "SELECT numero_adopcion FROM adopciones WHERE id_empleado = ? ORDER BY fecha_adopcion DESC";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEmpleado);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Adopcion adopcion = buscarPorId(rs.getInt("numero_adopcion"));
                if (adopcion != null) {
                    adopciones.add(adopcion);
                }
            }
        }
        return adopciones;
    }

    public boolean actualizar(Adopcion adopcion) throws SQLException {
        String sql = "UPDATE adopciones SET id_mascota = ?, id_adoptante = ?, id_empleado = ?, observaciones = ? WHERE numero_adopcion = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, adopcion.getMascota().getIdMascota());
            stmt.setInt(2, adopcion.getAdoptante().getIdAdoptante());
            stmt.setInt(3, adopcion.getEmpleado().getIdEmpleado());
            stmt.setString(4, "Adopción actualizada correctamente");
            stmt.setInt(5, adopcion.getNumeroAdopcion());

            return stmt.executeUpdate() > 0;
        }
    }

    public Adopcion buscarPorNumero(int numeroAdopcion) throws SQLException {
        return buscarPorId(numeroAdopcion);
    }

    public boolean eliminar(int numeroAdopcion) throws SQLException {
        Adopcion adopcion = buscarPorNumero(numeroAdopcion);
        if (adopcion == null) {
            return false;
        }

        MascotaDAO mascotaDAO = new MascotaDAO();
        mascotaDAO.marcarComoDisponible(adopcion.getMascota().getIdMascota());


        String sql = "DELETE FROM adopciones WHERE numero_adopcion = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, numeroAdopcion);
            return stmt.executeUpdate() > 0;
        }
    }

    public int contarAdopcionesPorMascota(int idMascota) throws SQLException {
        String sql = "SELECT COUNT(*) FROM adopciones WHERE id_mascota = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMascota);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public int contarAdopcionesPorAdoptante(int idAdoptante) throws SQLException {
        String sql = "SELECT COUNT(*) FROM adopciones WHERE id_adoptante = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAdoptante);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public List<Adopcion> obtenerTodasConDetalles() throws SQLException {
        return obtenerTodas();
    }
}