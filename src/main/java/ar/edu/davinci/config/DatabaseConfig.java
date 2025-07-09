package ar.edu.davinci.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConfig {
    private static final String DB_URL = "jdbc:h2:./data/adopciondb;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    private static DatabaseConfig instance;
    private Connection connection;

    private DatabaseConfig() {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            initializeDatabase();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Error al iniciar la base de datos", e);
        }
    }

    public static DatabaseConfig getInstance() {
        if (instance == null) {
            instance = new DatabaseConfig();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener conexion", e);
        }
        return connection;
    }

    private void initializeDatabase() throws SQLException {
        Statement stmt = connection.createStatement();

        // tabla empleados
        stmt.execute("""
            CREATE TABLE IF NOT EXISTS empleados (
                id_empleado INT PRIMARY KEY AUTO_INCREMENT,
                nombre VARCHAR(100) NOT NULL,
                apellido VARCHAR(100) NOT NULL,
                direccion VARCHAR(255),
                username VARCHAR(50) UNIQUE NOT NULL,
                password VARCHAR(255) NOT NULL,
                email VARCHAR(100),
                activo BOOLEAN DEFAULT TRUE,
                fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
        """);

        // tabla adoptantes
        stmt.execute("""
            CREATE TABLE IF NOT EXISTS adoptantes (
                id_adoptante INT PRIMARY KEY AUTO_INCREMENT,
                nombre VARCHAR(100) NOT NULL,
                apellido VARCHAR(100) NOT NULL,
                direccion VARCHAR(255) NOT NULL,
                edad INT NOT NULL,
                telefono VARCHAR(20),
                email VARCHAR(100),
                fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
        """);

        // tabla mascotas
        stmt.execute("""
            CREATE TABLE IF NOT EXISTS mascotas (
                id_mascota INT PRIMARY KEY AUTO_INCREMENT,
                nombre VARCHAR(100) NOT NULL,
                especie VARCHAR(50) NOT NULL,
                raza VARCHAR(100),
                fecha_nacimiento DATE,
                peso DECIMAL(5,2),
                temperatura DECIMAL(4,1) DEFAULT 36.5,
                estado VARCHAR(50) DEFAULT 'SALUDABLE',
                disponible BOOLEAN DEFAULT TRUE,
                fecha_ingreso TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
        """);

        // tabla adopciones
        stmt.execute("""
            CREATE TABLE IF NOT EXISTS adopciones (
                numero_adopcion INT PRIMARY KEY AUTO_INCREMENT,
                id_mascota INT NOT NULL,
                id_adoptante INT NOT NULL,
                id_empleado INT NOT NULL,
                fecha_adopcion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                observaciones TEXT,
                FOREIGN KEY (id_mascota) REFERENCES mascotas(id_mascota),
                FOREIGN KEY (id_adoptante) REFERENCES adoptantes(id_adoptante),
                FOREIGN KEY (id_empleado) REFERENCES empleados(id_empleado)
            )
        """);

        // tabla veterinarios
        stmt.execute("""
            CREATE TABLE IF NOT EXISTS veterinarios (
                id_veterinario INT PRIMARY KEY AUTO_INCREMENT,
                nombre VARCHAR(100) NOT NULL,
                apellido VARCHAR(100) NOT NULL,
                direccion VARCHAR(255),
                email VARCHAR(100) NOT NULL,
                activo BOOLEAN DEFAULT TRUE
            )
        """);

        //admin por defecto
        stmt.execute("""
            INSERT INTO empleados (nombre, apellido, direccion, username, password, email) 
            SELECT 'Admin', 'Sistema', 'Oficina Principal', 'admin', 'admin123', 'admin@veterinaria.com'
            WHERE NOT EXISTS (SELECT 1 FROM empleados WHERE username = 'admin')
        """);

        // Datos de prueba
        stmt.execute("""
            INSERT INTO mascotas (nombre, especie, raza, fecha_nacimiento, peso, temperatura, estado, disponible) 
            SELECT 'Luna', 'Gato', 'Persa', '2023-03-15', 3.5, 36.8, 'Saludable', true
            WHERE NOT EXISTS (SELECT 1 FROM mascotas WHERE nombre = 'Luna')
        """);

        stmt.execute("""
            INSERT INTO mascotas (nombre, especie, raza, fecha_nacimiento, peso, temperatura, estado, disponible) 
            SELECT 'Max', 'Perro', 'Labrador', '2022-08-20', 25.0, 37.2, 'Saludable', true
            WHERE NOT EXISTS (SELECT 1 FROM mascotas WHERE nombre = 'Max')
        """);

        stmt.execute("""
            INSERT INTO mascotas (nombre, especie, raza, fecha_nacimiento, peso, temperatura, estado, disponible) 
            SELECT 'Mimi', 'Gato', 'Siamés', '2023-01-10', 2.8, 36.5, 'Saludable', true
            WHERE NOT EXISTS (SELECT 1 FROM mascotas WHERE nombre = 'Mimi')
        """);

        stmt.execute("""
            INSERT INTO mascotas (nombre, especie, raza, fecha_nacimiento, peso, temperatura, estado, disponible) 
            SELECT 'Rocky', 'Perro', 'Pastor Alemán', '2021-11-05', 30.5, 37.0, 'Requiere Cuidados Especiales', true
            WHERE NOT EXISTS (SELECT 1 FROM mascotas WHERE nombre = 'Rocky')
        """);

        stmt.execute("""
            INSERT INTO mascotas (nombre, especie, raza, fecha_nacimiento, peso, temperatura, estado, disponible) 
            SELECT 'Bella', 'Gato', 'Común', '2023-06-12', 3.2, 36.9, 'Saludable', true
            WHERE NOT EXISTS (SELECT 1 FROM mascotas WHERE nombre = 'Bella')
        """);

        stmt.execute("""
            INSERT INTO mascotas (nombre, especie, raza, fecha_nacimiento, peso, temperatura, estado, disponible) 
            SELECT 'Toby', 'Perro', 'Golden Retriever', '2022-04-18', 28.0, 36.8, 'Saludable', true
            WHERE NOT EXISTS (SELECT 1 FROM mascotas WHERE nombre = 'Toby')
        """);

        stmt.execute("""
            INSERT INTO veterinarios (nombre, apellido, direccion, email, activo) 
            SELECT 'Dr. Carlos', 'Rodríguez', 'Av. Corrientes 1234', 'carlos.rodriguez@vet.com', true
            WHERE NOT EXISTS (SELECT 1 FROM veterinarios WHERE email = 'carlos.rodriguez@vet.com')
        """);

        stmt.execute("""
            INSERT INTO veterinarios (nombre, apellido, direccion, email, activo) 
            SELECT 'Dra. Ana', 'García', 'Calle Belgrano 567', 'ana.garcia@vet.com', true
            WHERE NOT EXISTS (SELECT 1 FROM veterinarios WHERE email = 'ana.garcia@vet.com')
        """);

        stmt.close();

        System.out.println("Base de datos inicializada correctamente");
    }

    public void cerrarConexion() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}