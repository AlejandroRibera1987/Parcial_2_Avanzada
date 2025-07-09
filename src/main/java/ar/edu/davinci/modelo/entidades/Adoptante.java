package ar.edu.davinci.modelo.entidades;

public class Adoptante extends Persona {
    private int idAdoptante;
    private int edad;
    private String telefono;
    private String email;

    public Adoptante(String nombre, String apellido, String direccion, int edad) {
        super(nombre, apellido, direccion);
        this.edad = edad;
    }

    public Adoptante(int idAdoptante, String nombre, String apellido, String direccion, int edad, String telefono, String email) {
        super(nombre, apellido, direccion);
        this.idAdoptante = idAdoptante;
        this.edad = edad;
        this.telefono = telefono;
        this.email = email;
    }

    // Getters y setters
    public int getIdAdoptante() {
        return idAdoptante;
    }

    public void setIdAdoptante(int idAdoptante) {
        this.idAdoptante = idAdoptante;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " (" + edad + " años)";
    }
}