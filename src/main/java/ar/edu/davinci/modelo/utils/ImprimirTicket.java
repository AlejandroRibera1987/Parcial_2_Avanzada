package ar.edu.davinci.modelo.utils;

import ar.edu.davinci.modelo.entidades.Adopcion;

public class ImprimirTicket implements GeneradorTicket {

    private Adopcion adopcion;

    public ImprimirTicket(Adopcion adopcion){
        this.adopcion = adopcion;
    }

    @Override
    public String generarTicket() {
        return  String.format("""
            =====================================
                 TICKET DE ADOPCIÓN #%d
            ======================================
            Fecha y Hora: %s
            Datos del adoptante:
            Nombre: %s %s
            Edad: %d
            Dirección: %s
            
            Datos de la Mascota:
            Nombre: %s
            Fecha de Nacimiento: %s
            Peso: %.2f kg
            Raza: %s
            Especie: %s
            
            Datos del empleado:
            Nombre: %s %s
            Id Empleado: %d
            =====================================================
                       GRACIAS POR LA ADOPCIÓN
            =======================================================
            """,
                adopcion.getNumeroAdopcion(),
                adopcion.getFecha(),
                adopcion.getAdoptante().getNombre(),
                adopcion.getAdoptante().getApellido(),
                adopcion.getAdoptante().getEdad(),
                adopcion.getAdoptante().getDireccion(),
                adopcion.getMascota().getNombre(),
                adopcion.getMascota().getFechaNacimiento(),
                adopcion.getMascota().getPeso(),
                adopcion.getMascota().getRaza(),
                adopcion.getMascota().getEspecie(),
                adopcion.getEmpleado().getNombre(),
                adopcion.getEmpleado().getApellido(),
                adopcion.getEmpleado().getIdEmpleado()
        );
    }

    public void imprimirTicket(){
        System.out.println(generarTicket());
    }
}
