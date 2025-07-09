package ar.edu.davinci.dao;

import ar.edu.davinci.modelo.entidades.Adoptante;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdoptanteDAO extends BaseDAO {

    public int crear(Adoptante adoptante) throws SQLException {
        String sql = "INSERT INTO adoptantes (nombre, apellido, direccion, edad, telefono, email) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, adoptante.getNombre());
            stmt.setString(2, adoptante.getApellido());
            stmt.setString(3, adoptante.getDireccion());
            stmt.setInt(4, adoptante.getEdad());
            stmt.setString(5, adoptante.getTelefono());
            stmt.setString(6, adoptante.getEmail());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Error al crear adoptante");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    adoptante.setIdAdoptante(id);
                    return id;
                }
            }
        }
        return 0;
    }

    public Adoptante buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM adoptantes WHERE id_adoptante = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Adoptante(
                        rs.getInt("id_adoptante"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("direccion"),
                        rs.getInt("edad"),
                        rs.getString("telefono"),
                        rs.getString("email")
                );
            }
        }
        return null;
    }

    public List<Adoptante> obtenerTodos() throws SQLException {
        List<Adoptante> adoptantes = new ArrayList<>();
        String sql = "SELECT * FROM adoptantes ORDER BY nombre, apellido";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                adoptantes.add(new Adoptante(
                        rs.getInt("id_adoptante"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("direccion"),
                        rs.getInt("edad"),
                        rs.getString("telefono"),
                        rs.getString("email")
                ));
            }
        }
        return adoptantes;
    }

    public boolean actualizar(Adoptante adoptante) throws SQLException {
        String sql = "UPDATE adoptantes SET nombre = ?, apellido = ?, direccion = ?, edad = ?, telefono = ?, email = ? WHERE id_adoptante = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, adoptante.getNombre());
            stmt.setString(2, adoptante.getApellido());
            stmt.setString(3, adoptante.getDireccion());
            stmt.setInt(4, adoptante.getEdad());
            stmt.setString(5, adoptante.getTelefono());
            stmt.setString(6, adoptante.getEmail());
            stmt.setInt(7, adoptante.getIdAdoptante());

            return stmt.executeUpdate() > 0;
        }
    }
}