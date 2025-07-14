package ar.edu.davinci.controlador;

import ar.edu.davinci.modelo.entidades.Adopcion;
import ar.edu.davinci.vista.adopcion.DetallesAdopcionView;
import ar.edu.davinci.vista.adopcion.ModificarAdopcionView;

import javax.swing.*;

public class DetallesAdopcionController {
    private DetallesAdopcionView detallesView;
    private Adopcion adopcion;

    public DetallesAdopcionController(DetallesAdopcionView detallesView) {
        this.detallesView = detallesView;
        this.adopcion = detallesView.getAdopcion();

        initEventListeners();
        cargarDatos();
    }

    private void initEventListeners() {
        detallesView.getBtnModificar().addActionListener(e -> modificarAdopcion());

        detallesView.getBtnImprimir().addActionListener(e -> imprimirDetalles());

        detallesView.getBtnCerrar().addActionListener(e -> detallesView.dispose());
    }

    private void cargarDatos() {
        try {

            detallesView.getLblIdAdopcion().setText(String.valueOf(adopcion.getNumeroAdopcion()));

            String fechaFormateada = formatearFecha(adopcion.getFecha());
            detallesView.getLblFechaAdopcion().setText(fechaFormateada);
            detallesView.getLblEstado().setText("Activa"); // Puedes agregar campo estado a Adopcion

            detallesView.getLblMascotaNombre().setText(adopcion.getMascota().getNombre());
            detallesView.getLblMascotaEspecie().setText(adopcion.getMascota().getEspecie());
            detallesView.getLblMascotaRaza().setText(adopcion.getMascota().getRaza());
            detallesView.getLblMascotaEdad().setText(calcularEdadMascota() + " años");
            detallesView.getLblMascotaColor().setText("Información no disponible"); // Agregar campo si existe
            detallesView.getLblMascotaTamanio().setText(clasificarTamanio(adopcion.getMascota().getPeso()));

            detallesView.getLblAdoptanteNombre().setText(
                    adopcion.getAdoptante().getNombre() + " " + adopcion.getAdoptante().getApellido()
            );
            detallesView.getLblAdoptanteDni().setText("No disponible"); // Agregar campo si existe
            detallesView.getLblAdoptanteEmail().setText(
                    adopcion.getAdoptante().getEmail() != null ?
                            adopcion.getAdoptante().getEmail() : "No proporcionado"
            );
            detallesView.getLblAdoptanteTelefono().setText(
                    adopcion.getAdoptante().getTelefono() != null ?
                            adopcion.getAdoptante().getTelefono() : "No proporcionado"
            );
            detallesView.getLblAdoptanteDireccion().setText(adopcion.getAdoptante().getDireccion());

            detallesView.getLblEmpleadoNombre().setText(
                    adopcion.getEmpleado().getNombre() + " " + adopcion.getEmpleado().getApellido()
            );
            detallesView.getLblEmpleadoLegajo().setText("EMP" + String.format("%03d", adopcion.getEmpleado().getIdEmpleado()));
            detallesView.getLblEmpleadoCargo().setText("Empleado"); // Agregar campo si existe

            String observaciones = generarObservaciones();
            detallesView.getTxtObservaciones().setText(observaciones);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(detallesView,
                    "Error al cargar los datos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String formatearFecha(String fechaString) {
        try {
            // La fecha viene como String del DAO, extraer solo la parte de la fecha
            // Formato esperado: "2024-01-15 10:30:00.0" -> "15 de Enero de 2024"
            String fechaSolo = fechaString.split(" ")[0]; // Tomar solo la parte de fecha
            String[] partes = fechaSolo.split("-");

            if (partes.length == 3) {
                int año = Integer.parseInt(partes[0]);
                int mes = Integer.parseInt(partes[1]);
                int dia = Integer.parseInt(partes[2]);

                String[] meses = {"", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

                return dia + " de " + meses[mes] + " de " + año;
            }
        } catch (Exception e) {
            // Si hay error en el formato, devolver la fecha original
            System.err.println("Error al formatear fecha: " + e.getMessage());
        }
        return fechaString;
    }

    private String calcularProximaVisita(String fechaAdopcion) {
        try {

            String fechaSolo = fechaAdopcion.split(" ")[0]; // "2024-01-15"
            String[] partes = fechaSolo.split("-");

            if (partes.length == 3) {
                int año = Integer.parseInt(partes[0]);
                int mes = Integer.parseInt(partes[1]);
                int dia = Integer.parseInt(partes[2]);

                mes += 1;
                if (mes > 12) {
                    mes = 1;
                    año += 1;
                }

                String[] meses = {"", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

                return dia + " de " + meses[mes] + " de " + año;
            }
        } catch (Exception e) {
            System.err.println("Error al calcular próxima visita: " + e.getMessage());
        }
        return "A coordinar";
    }

    private String calcularEdadMascota() {
        return "No especificada";
    }

    private String clasificarTamanio(double peso) {
        if (peso < 5) {
            return "Pequeño";
        } else if (peso < 20) {
            return "Mediano";
        } else {
            return "Grande";
        }
    }

    private String generarObservaciones() {
        StringBuilder observaciones = new StringBuilder();

        observaciones.append("La adopción se realizó exitosamente. ");
        observaciones.append("La mascota ").append(adopcion.getMascota().getNombre());
        observaciones.append(" mostró una excelente adaptación con la familia adoptante.\n\n");

        if (adopcion.getMascota().getRecomendaciones() != null &&
                !adopcion.getMascota().getRecomendaciones().trim().isEmpty()) {
            observaciones.append("Recomendaciones específicas:\n");
            observaciones.append(adopcion.getMascota().getRecomendaciones()).append("\n\n");
        }

        observaciones.append("Se requiere seguimiento mensual durante los primeros 6 meses ");
        observaciones.append("para asegurar el bienestar del animal.\n\n");

        try {
            String fechaProximaVisita = calcularProximaVisita(adopcion.getFecha());
            observaciones.append("Próxima visita programada: ").append(fechaProximaVisita).append("\n");
        } catch (Exception e) {
            observaciones.append("Próxima visita programada: A coordinar\n");
        }
        observaciones.append("Vacunas al día: Sí\n");
        observaciones.append("Estado de salud: ").append(adopcion.getMascota().getEstado().getNombreEstado());

        return observaciones.toString();
    }

    private void modificarAdopcion() {
        ModificarAdopcionView modificar = new ModificarAdopcionView(adopcion, null);
        new ModificarAdopcionController(modificar);
        modificar.setVisible(true);
        detallesView.dispose();
    }

    private void imprimirDetalles() {
        try {
            String contenidoImpresion = generarReporteCompleto();

            JTextArea textArea = new JTextArea(contenidoImpresion);
            textArea.setEditable(false);
            textArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 11));
            textArea.setBackground(new java.awt.Color(248, 248, 248));

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new java.awt.Dimension(600, 500));

            int opcion = JOptionPane.showOptionDialog(detallesView,
                    scrollPane,
                    "Reporte Completo de Adopción #" + adopcion.getNumeroAdopcion(),
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new String[]{"Imprimir", "Cerrar"},
                    "Imprimir");

            if (opcion == 0) {
                JOptionPane.showMessageDialog(detallesView,
                        "Reporte enviado a la impresora.\n(Funcionalidad de impresión simulada)",
                        "Impresión",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(detallesView,
                    "Error al generar reporte: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generarReporteCompleto() {
        return String.format("""
            ================================================================
                           REPORTE COMPLETO DE ADOPCIÓN
            ================================================================
            
            INFORMACIÓN GENERAL
            • Número de adopción: %d
            • Fecha de adopción: %s
            • Estado: Activa
            • Fecha de reporte: %s
            
            ================================================================
            DATOS DE LA MASCOTA
            ================================================================
            • Nombre: %s
            • Especie: %s
            • Raza: %s
            • Peso: %.2f kg
            • Tamaño: %s
            • Fecha de nacimiento: %s
            • Estado de salud: %s
            • Recomendaciones: %s
            
            ================================================================
            DATOS DEL ADOPTANTE
            ================================================================
            • Nombre completo: %s %s
            • Edad: %d años
            • Dirección: %s
            • Teléfono: %s
            • Email: %s
            
            ================================================================
            EMPLEADO RESPONSABLE
            ================================================================
            • Nombre: %s %s
            • ID Empleado: %d
            • Legajo: EMP%03d
            
            ================================================================
            OBSERVACIONES Y SEGUIMIENTO
            ================================================================
            %s
            
            ================================================================
            Este documento certifica que la adopción fue procesada 
            correctamente según los procedimientos establecidos.
            
            Refugio de Mascotas - Sistema de Gestión de Adopciones
            ================================================================
            """,
                adopcion.getNumeroAdopcion(),
                formatearFecha(adopcion.getFecha()),
                new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date()),
                adopcion.getMascota().getNombre(),
                adopcion.getMascota().getEspecie(),
                adopcion.getMascota().getRaza(),
                adopcion.getMascota().getPeso(),
                clasificarTamanio(adopcion.getMascota().getPeso()),
                adopcion.getMascota().getFechaNacimiento() != null ?
                        adopcion.getMascota().getFechaNacimiento().toString() : "No especificada",
                adopcion.getMascota().getEstado().getNombreEstado(),
                adopcion.getMascota().getRecomendaciones() != null ?
                        adopcion.getMascota().getRecomendaciones() : "Ninguna específica",
                adopcion.getAdoptante().getNombre(),
                adopcion.getAdoptante().getApellido(),
                adopcion.getAdoptante().getEdad(),
                adopcion.getAdoptante().getDireccion(),
                adopcion.getAdoptante().getTelefono() != null ?
                        adopcion.getAdoptante().getTelefono() : "No proporcionado",
                adopcion.getAdoptante().getEmail() != null ?
                        adopcion.getAdoptante().getEmail() : "No proporcionado",
                adopcion.getEmpleado().getNombre(),
                adopcion.getEmpleado().getApellido(),
                adopcion.getEmpleado().getIdEmpleado(),
                adopcion.getEmpleado().getIdEmpleado(),
                generarObservaciones()
        );
    }
}