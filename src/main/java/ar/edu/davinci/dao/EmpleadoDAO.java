package ar.edu.davinci.dao;

import ar.edu.davinci.modelo.entidades.Empleado;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO extends BaseDAO {

    public boolean validarLogin(String username, String password) throws SQLException {

        String sql = "SELECT COUNT(*) FROM empleados WHERE username = ? AND password = ? AND activo = TRUE";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            boolean resultado = rs.next() && rs.getInt(1) > 0;

            return resultado;
        } catch (SQLException e) {
            throw e;
        }
    }

    public Empleado buscarPorUsername(String username) throws SQLException {

        String sql = "SELECT * FROM empleados WHERE username = ? AND activo = TRUE";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Empleado empleado = new Empleado(
                        rs.getInt("id_empleado"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("direccion"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")
                );

                return empleado;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    public Empleado buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM empleados WHERE id_empleado = ? AND activo = TRUE";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Empleado(
                        rs.getInt("id_empleado"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("direccion"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")
                );
            }
        }
        return null;
    }

    public boolean existeUsername(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM empleados WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    public int crear(Empleado empleado) throws SQLException {
        String sql = "INSERT INTO empleados (nombre, apellido, direccion, username, password, email) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getApellido());
            stmt.setString(3, empleado.getDireccion());
            stmt.setString(4, empleado.getUsername());
            stmt.setString(5, empleado.getPassword());
            stmt.setString(6, empleado.getEmail());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Error al crear empleado");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    empleado.setIdEmpleado(id);
                    return id;
                }
            }
        }
        return 0;
    }

    public List<Empleado> obtenerTodos() throws SQLException {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM empleados WHERE activo = TRUE ORDER BY nombre, apellido";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                empleados.add(new Empleado(
                        rs.getInt("id_empleado"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("direccion"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")
                ));
            }
        }
        return empleados;
    }
}