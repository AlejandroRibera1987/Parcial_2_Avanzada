package ar.edu.davinci.modelo.entidades;

public class Gato extends Mascota {

    public Gato(String nombre, String fechaNacimiento, double peso, String raza) {
        super(nombre, fechaNacimiento, peso, raza);
    }

    public Gato(int idMascota, String nombre, String fechaNacimiento, double peso, String raza, double temperatura, String estadoNombre) {
        super(idMascota, nombre, fechaNacimiento, peso, raza, temperatura, estadoNombre);
    }

    @Override
    public String getEspecie() {
        return "Gato";
    }

    @Override
    public String getRecomendacionesBasicas() {
        return "RECOMENDACIONES BASICAS PARA GATOS:\n" +
                "- Alimentación: 2-3 veces al día con comida para gatos\n" +
                "- Agua fresca disponible siempre\n" +
                "- Caja de arena limpia diariamente\n" +
                "- Juguetes para estimulación mental\n" +
                "- Rascador para mantener uñas saludables\n" +
                "- Visitas regulares al veterinario\n" +
                "- Espacio seguro en casa\n" +
                "- Cepillado regular del pelaje";
    }
}