package ar.edu.davinci.dao;

import ar.edu.davinci.modelo.entidades.Mascota;
import ar.edu.davinci.modelo.entidades.Gato;
import ar.edu.davinci.modelo.entidades.Perro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MascotaDAO extends BaseDAO {

    public int crear(Mascota mascota) throws SQLException {
        String sql = "INSERT INTO mascotas (nombre, especie, raza, fecha_nacimiento, peso, temperatura, estado, disponible) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, mascota.getNombre());
            stmt.setString(2, mascota.getEspecie());
            stmt.setString(3, mascota.getRaza());
            stmt.setString(4, mascota.getFechaNacimiento());
            stmt.setDouble(5, mascota.getPeso());
            stmt.setDouble(6, mascota.getTemperatura());
            stmt.setString(7, mascota.getEstado().getNombreEstado());
            stmt.setBoolean(8, mascota.isDisponible());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Error al crear mascota");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    mascota.setIdMascota(id);
                    return id;
                }
            }
        }
        return 0;
    }

    public Mascota buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM mascotas WHERE id_mascota = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return crearMascotaDesdeResultSet(rs);
            }
        }
        return null;
    }

    public List<Mascota> obtenerDisponibles() throws SQLException {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT * FROM mascotas WHERE disponible = TRUE ORDER BY nombre";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                mascotas.add(crearMascotaDesdeResultSet(rs));
            }
        }
        return mascotas;
    }

    public List<Mascota> obtenerTodas() throws SQLException {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT * FROM mascotas ORDER BY nombre";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                mascotas.add(crearMascotaDesdeResultSet(rs));
            }
        }
        return mascotas;
    }

    public boolean marcarComoAdoptada(int idMascota) throws SQLException {
        String sql = "UPDATE mascotas SET disponible = FALSE WHERE id_mascota = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMascota);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean actualizarEstado(int idMascota, String nuevoEstado) throws SQLException {
        String sql = "UPDATE mascotas SET estado = ? WHERE id_mascota = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, idMascota);
            return stmt.executeUpdate() > 0;
        }
    }

    private Mascota crearMascotaDesdeResultSet(ResultSet rs) throws SQLException {
        String especie = rs.getString("especie");

        if ("Gato".equalsIgnoreCase(especie)) {
            return new Gato(
                    rs.getInt("id_mascota"),
                    rs.getString("nombre"),
                    rs.getString("fecha_nacimiento"),
                    rs.getDouble("peso"),
                    rs.getString("raza"),
                    rs.getDouble("temperatura"),
                    rs.getString("estado")
            );
        } else if ("Perro".equalsIgnoreCase(especie)) {
            return new Perro(
                    rs.getInt("id_mascota"),
                    rs.getString("nombre"),
                    rs.getString("fecha_nacimiento"),
                    rs.getDouble("peso"),
                    rs.getString("raza"),
                    rs.getDouble("temperatura"),
                    rs.getString("estado")
            );
        }
        throw new SQLException("Especie de mascota no reconocida: " + especie);
    }

    public boolean marcarComoDisponible(int idMascota) throws SQLException {
        String sql = "UPDATE mascotas SET disponible = TRUE WHERE id_mascota = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMascota);
            return stmt.executeUpdate() > 0;
        }
    }
}