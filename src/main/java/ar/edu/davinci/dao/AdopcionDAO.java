package ar.edu.davinci.dao;

import ar.edu.davinci.modelo.entidades.Adopcion;
import ar.edu.davinci.modelo.entidades.Mascota;
import ar.edu.davinci.modelo.entidades.Adoptante;
import ar.edu.davinci.modelo.entidades.Empleado;
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
        String sql = "SELECT * FROM adopciones WHERE numero_adopcion = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, numeroAdopcion);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                MascotaDAO mascotaDAO = new MascotaDAO();
                AdoptanteDAO adoptanteDAO = new AdoptanteDAO();
                EmpleadoDAO empleadoDAO = new EmpleadoDAO();

                Mascota mascota = mascotaDAO.buscarPorId(rs.getInt("id_mascota"));
                Adoptante adoptante = adoptanteDAO.buscarPorId(rs.getInt("id_adoptante"));
                Empleado empleado = empleadoDAO.buscarPorId(rs.getInt("id_empleado"));

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
        String sql = "SELECT numero_adopcion FROM adopciones ORDER BY fecha_adopcion DESC";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Adopcion adopcion = buscarPorId(rs.getInt("numero_adopcion"));
                if (adopcion != null) {
                    adopciones.add(adopcion);
                }
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
        String sql = "UPDATE adopciones SET observaciones = ? WHERE numero_adopcion = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "Actualizada");
            stmt.setInt(2, adopcion.getNumeroAdopcion());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int numeroAdopcion) throws SQLException {
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
}