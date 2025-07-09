package ar.edu.davinci.modelo.entidades;

public class Empleado extends Persona {
    private int idEmpleado;
    private String username;
    private String password;
    private String email;
    private boolean activo;

    public Empleado(String nombre, String apellido, String direccion, String username, String password) {
        super(nombre, apellido, direccion);
        this.username = username;
        this.password = password;
        this.activo = true;
    }

    public Empleado(int idEmpleado, String nombre, String apellido, String direccion, String username, String password, String email) {
        super(nombre, apellido, direccion);
        this.idEmpleado = idEmpleado;
        this.username = username;
        this.password = password;
        this.email = email;
        this.activo = true;
    }


    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " (ID: " + idEmpleado + ")";
    }
}