package ar.edu.davinci.modelo.entidades;

public class Perro extends Mascota {

    public Perro(String nombre, String fechaNacimiento, double peso, String raza) {
        super(nombre, fechaNacimiento, peso, raza);
    }

    public Perro(int idMascota, String nombre, String fechaNacimiento, double peso, String raza, double temperatura, String estadoNombre) {
        super(idMascota, nombre, fechaNacimiento, peso, raza, temperatura, estadoNombre);
    }

    @Override
    public String getEspecie() {
        return "Perro";
    }

    @Override
    public String getRecomendacionesBasicas() {
        return "RECOMENDACIONES BÁSICAS PARA PERROS:\n" +
                "- Alimentación: 2-3 veces al día con comida para perros\n" +
                "- Agua fresca disponible siempre\n" +
                "- Paseos diarios para ejercicio\n" +
                "- Socialización con otros perros\n" +
                "- Juguetes para masticar\n" +
                "- Entrenamiento básico de obediencia\n" +
                "- Visitas regulares al veterinario\n" +
                "- Espacio adecuado para correr y jugar\n" +
                "- Cepillado regular del pelaje";
    }
}