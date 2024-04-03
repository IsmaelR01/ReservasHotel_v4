package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.utilidades.Entrada;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import org.iesalandalus.programacion.reservashotel.modelo.Modelo.*;




public class Consola {
    private static Vista vista;
    private static Controlador controlador;
    private Consola() {

    }

    public static void mostrarMenu() {
        System.out.println("Menu de Opciones");
        Opcion[] opciones = Opcion.values();
        for (int i = 0; i < opciones.length; i++) {
            System.out.println(opciones[i].toString());
        }
    }

    public static Opcion elegirOpcion() {
        int opcionEscogida;
        do {
            System.out.println("Elija  una opción válida");
            opcionEscogida = Entrada.entero();
            if (opcionEscogida < 1 || opcionEscogida > Opcion.values().length) {
                System.out.println("Opción incorrecta, inténtalo de nuevo.");
            }
        } while (opcionEscogida < 1 || opcionEscogida > Opcion.values().length);
        return Opcion.values()[opcionEscogida - 1];
    }

    public static Huesped leerHuesped() {
        System.out.println("Introduce los datos del huésped");
        System.out.println("Nombre");
        String nombre = Entrada.cadena();
        System.out.println("DNI");
        String dni = Entrada.cadena();
        System.out.println("Correo electrónico");
        String correo = Entrada.cadena();
        System.out.println("Número de teléfono");
        String telefono = Entrada.cadena();
        return new Huesped(nombre, dni, correo, telefono, leerfecha("Fecha de nacimiento formato (dd/MM/YYYY)"));
    }

    public static Huesped getHuespedPorDni() {
        System.out.println("Introduce el dni del huésped");
        String dni = Entrada.cadena();
        /*
        ArrayList<Huesped> busquedaHuesped = huespedes.get(); // Obtener el array de huéspedes
        for (int i = 0; i < busquedaHuesped.size(); i++) {
            Huesped huesped = busquedaHuesped.get(i);
            if (huesped.getDni().equals(dni)) {
                return new Huesped(huesped.getNombre(), dni, huesped.getCorreo(), huesped.getTelefono(), huesped.getFechaNacimiento());
            }
        }

         */
        return new Huesped("Spiderman Rodríguez Cabrera",dni,"spidermanr46@gmail.com","675679876",LocalDate.of(2001,5,23));
    }


    public static LocalDate leerfecha(String mensaje) {
        LocalDate fechaFinal = null;
        String fecha;
        do {
            System.out.println(mensaje);
            fecha = Entrada.cadena();
            try {
                fechaFinal = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha no válido. Por favor, introduzca la fecha  formato (dd/MM/yyyy)");
            }
        } while (fechaFinal == null);
        return fechaFinal;
    }

    public static LocalDateTime leerFechaHora(String mensaje) {
        String fecha;
        LocalDateTime fechaFinal = null;
        do {
            System.out.println(mensaje);
            fecha = Entrada.cadena();
            try {
                fechaFinal = LocalDateTime.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha y hora no válido. Por favor, introduzca la fecha y hora formato (dd/MM/yyyy HH:mm:ss)");
            }
        } while (fechaFinal == null);
        return fechaFinal;
    }


    public static Habitacion leerHabitacion() {
        System.out.println("Introduce los datos de la habitación");
        System.out.println("Planta");
        int planta = Entrada.entero();
        System.out.println("Puerta");
        int puerta = Entrada.entero();
        System.out.println("Precio");
        double precio = Entrada.realDoble();
        switch (leerTipoHabitacion()) {
            case SIMPLE:
                return new Simple(planta,puerta,precio);

            case DOBLE:
                System.out.println("Introduce camas individuales 0-2");
                int numCamasIndividualesDoble = Entrada.entero();
                System.out.println("Introduce camas dobles 0 o 1");
                int numCamasDoblesDoble = Entrada.entero();
                return new Doble(planta,puerta,precio,numCamasIndividualesDoble,numCamasDoblesDoble);

            case TRIPLE:
                System.out.println("Introduce numero de baños 1 o 2");
                int numBanosTriple = Entrada.entero();
                System.out.println("Introduce camas individuales 1-3");
                int numCamasIndividualesTriple = Entrada.entero();
                System.out.println("Introduce camas dobles 0 o 1");
                int numCamasDoblesTriple = Entrada.entero();
                return new Triple(planta,puerta,precio,numBanosTriple,numCamasIndividualesTriple,numCamasDoblesTriple);

            case SUITE:
                System.out.println("Introduce numero de baños 1 o 2");
                int numBanosSuite = Entrada.entero();
                char respuesta;
                boolean jacuzzi;
                do {
                    System.out.println("¿Quieres la habitación con jacuzzi? (s/n)");
                    respuesta = Character.toLowerCase(Entrada.caracter());
                    if (respuesta != 's' && respuesta != 'n') {
                        System.out.println("Respuesta incorrecta, por favor ingrese 's' o 'n'.");
                    }
                } while (respuesta != 's' && respuesta != 'n');
                if (respuesta == 's') {
                    System.out.println("Has elegido la suite con jacuzzi");
                } else {
                    System.out.println("Has elegido la suite sin jacuzzi");
                }
                if(respuesta == 's') {
                    jacuzzi = true;
                }else {
                    jacuzzi = false;
                }
                return new Suite(planta,puerta,precio,numBanosSuite,jacuzzi);
        }
        return null;
    }


    public static Habitacion leerHabitacionPorIdentificador() {
            System.out.println("Introduce el numero de planta");
            int planta = Entrada.entero();
            System.out.println("Introduce el número de puerta");
            int puerta = Entrada.entero();
            return new Simple(planta,puerta,60);
            /*
            String identificador = "" + planta + puerta;
        ArrayList<Habitacion> busquedaHabitacion = habitaciones.get();
        for (int i = 0; i < busquedaHabitacion.size(); i++) {
            Habitacion habitacion = busquedaHabitacion.get(i);
            if(habitacion.getIdentificador().equals(identificador) && habitacion instanceof Simple) {
                return new Simple((Simple) habitacion);
            }
            if(habitacion.getIdentificador().equals(identificador) && habitacion instanceof Doble) {
                return new Doble((Doble) habitacion);
            }
            if(habitacion.getIdentificador().equals(identificador) && habitacion instanceof Triple) {
                return new Triple((Triple) habitacion);
            }
            if(habitacion.getIdentificador().equals(identificador) && habitacion instanceof Suite) {
                return new Suite((Suite) habitacion);
            }
        }

             */
    }

    public static TipoHabitacion leerTipoHabitacion() {
        int opcionEscogida;
        do {
            System.out.println("Menu de Opciones Tipos de Habitación");
            TipoHabitacion[] tipoHabitacion = TipoHabitacion.values();
            for (int i = 0; i < tipoHabitacion.length; i++) {
                System.out.println(tipoHabitacion[i].toString());
            }
            System.out.println("Elija un tipo de habitación");
            opcionEscogida = Entrada.entero();
            if (opcionEscogida < 1 || opcionEscogida > TipoHabitacion.values().length) {
                System.out.println("Opción incorrecta, inténtalo de nuevo.");
            }
        } while (opcionEscogida < 1 || opcionEscogida > TipoHabitacion.values().length);
        return TipoHabitacion.values()[opcionEscogida - 1];
    }


    public static Regimen leerRegimen() {
        int opcionEscogida;
        do {
            System.out.println("Menu de Opciones Régimen");
            Regimen[] regimen = Regimen.values();
            for (int i = 0; i < regimen.length; i++) {
                System.out.println(regimen[i].toString());
            }
            System.out.println("Elija un Régimen");
            opcionEscogida = Entrada.entero();
            if (opcionEscogida < 1 || opcionEscogida > Regimen.values().length) {
                System.out.println("Opción incorrecta, inténtalo de nuevo.");
            }
        } while (opcionEscogida < 1 || opcionEscogida > Regimen.values().length);
        return Regimen.values()[opcionEscogida - 1];
    }

}



