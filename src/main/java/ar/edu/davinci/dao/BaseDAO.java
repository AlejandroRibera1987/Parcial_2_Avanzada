package ar.edu.davinci.dao;

import ar.edu.davinci.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseDAO {

    protected Connection getConnection() throws SQLException {
        try {
            Connection conn = DatabaseConfig.getInstance().getConnection();
            if (conn == null) {
                throw new SQLException("No se pudo obtener conexion a la base de datos");
            }
            System.out.println("Conexión a H2 establecida correctamente");
            return conn;
        } catch (Exception e) {
            System.err.println("Error al conectar con H2: " + e.getMessage());
            e.printStackTrace();
            throw new SQLException("Error de conexion: " + e.getMessage(), e);
        }
    }

    protected void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("🔌 Conexion a H2 cerrada");
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexion: " + e.getMessage());
            }
        }
    }
}

