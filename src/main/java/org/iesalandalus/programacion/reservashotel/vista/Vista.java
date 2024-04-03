package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;

import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Vista {
    private static Controlador controlador;
    public Vista() {

    }


    public void setControlador(Controlador controlador) {
        if(controlador == null) {
            throw new NullPointerException("ERROR: El controlador no puede ser nulo.");
        }
        this.controlador = controlador;
        Opcion.setVista(this);
    }

    public void comenzar() {
        Opcion opcion;
        /*
        arrancar();

         */
        do {
            Consola.mostrarMenu();
            opcion = Consola.elegirOpcion();
            opcion.ejecutar();
        }while(opcion != Opcion.SALIR);
    }
/*
    private void arrancar(){
        try {
            controlador.insertar(new Huesped("Huesped1", "66443311Z", "correo1@iesalandalus.es", "650102030", LocalDate.of(2000, 1, 1)));
            controlador.insertar(new Huesped("Huesped2", "99887755C", "correo2@iesalandalus.es", "650102030", LocalDate.of(2000, 1, 1)));
            controlador.insertar(new Huesped("Huesped3", "11223344B", "correo3@iesalandalus.es", "650102030", LocalDate.of(2000, 1, 1)));
            controlador.insertar(new Huesped("Huesped4", "55667788Z", "correo4@iesalandalus.es", "650102030", LocalDate.of(2000, 1, 1)));


            controlador.insertar(new Simple(1, 1, 50));
            controlador.insertar(new Doble(1, 2, 60, 2,0));
            controlador.insertar(new Triple(2, 1, 100,1,1,1));
            controlador.insertar(new Suite(2, 2, 140,1, true));
            controlador.insertar(new Suite(2, 3, 140,1, true));

            Habitacion h = controlador.buscar(controlador.getHabitaciones().get(0));
            controlador.insertar(new Reserva(controlador.getHuespedes().get(0), h, Regimen.MEDIA_PENSION, LocalDate.now().plusDays(1), LocalDate.now().plusDays(3), 1));
            h = controlador.buscar(controlador.getHabitaciones().get(3));
            controlador.insertar(new Reserva(controlador.getHuespedes().get(0), h, Regimen.MEDIA_PENSION, LocalDate.now().plusDays(5), LocalDate.now().plusDays(24), 4));
        }catch(OperationNotSupportedException e){
            System.out.println("ERROR: ERROR FATAL, el programa no arranca correctamente!!!");
        }
    }

 */

    public void terminar() {
        controlador.terminar();
    }


    public void insertarHuesped() {
        try {
            controlador.insertar(Consola.leerHuesped());
            System.out.println("Huésped insertado correctamente.");
        }catch(NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            System.out.println("-" + e.getMessage());
        }
    }

    public void buscarHuesped() {
        try {
            Huesped huesped = controlador.buscar(Consola.getHuespedPorDni());
            if(huesped == null) {
                System.out.println("El huésped introducido no existe.");
            }else {
                System.out.println(huesped);
            }
        }catch (NullPointerException | IllegalArgumentException e) {
            System.out.println("-" + e.getMessage());
        }
    }

    public void borrarHuesped() {
        try {
            controlador.borrar(Consola.getHuespedPorDni());
            System.out.println("Huesped borrado correctamente.");
        }catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            System.out.println("-" + e.getMessage());
        }
    }

    public void mostrarHuespedes() {
        if(controlador.getHuespedes().isEmpty()) {
            System.out.println("No hay huéspedes para mostrar.");
        }else{
            ArrayList<Huesped> huespedesOrdenados = controlador.getHuespedes();
            Collections.sort(huespedesOrdenados,Comparator.comparing(Huesped::getNombre));
            Iterator<Huesped> huespedesIterador = huespedesOrdenados.iterator();
            while(huespedesIterador.hasNext()) {
                Huesped huesped = huespedesIterador.next();
                System.out.println(huesped.toString());
            }
            /*
            for(int i = 0; i<controlador.getHuespedes().length;i++) {
                System.out.println(controlador.getHuespedes()[i].toString());
            }

             */
        }
    }

    public void insertarHabitacion() {
        try {
            controlador.insertar(Consola.leerHabitacion());
            System.out.println("Habitación insertada correctamente");
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            System.out.println("-" + e.getMessage());
        }
    }
    public void buscarHabitacion() {
        try {
            Habitacion habitacion = controlador.buscar(Consola.leerHabitacionPorIdentificador());
            if(habitacion == null) {
                System.out.println("La habitación introducida no existe.");
            }else {
                System.out.println(habitacion);
            }
        }catch (NullPointerException | IllegalArgumentException e) {
            System.out.println("-" + e.getMessage());
        }
    }

    public void borrarHabitacion() {
        try {
            controlador.borrar(Consola.leerHabitacionPorIdentificador());
            System.out.println("Habitación borrada correctamente.");
        }catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            System.out.println("-" + e.getMessage());
        }
    }

    public void mostrarHabitaciones() {
        if(controlador.getHabitaciones().isEmpty()) {
            System.out.println("No hay habitaciones para mostrar.");
        }else{
            ArrayList<Habitacion> habitacionesOrdenadas = controlador.getHabitaciones();
            Collections.sort(habitacionesOrdenadas,Comparator.comparing(Habitacion::getIdentificador));
            Iterator<Habitacion> habitacionesIterador = habitacionesOrdenadas.iterator();
            while(habitacionesIterador.hasNext()) {
                Habitacion habitacion = habitacionesIterador.next();
                System.out.println(habitacion.toString());
            }
            /*
            for(int i = 0; i<controlador.getHabitaciones().length;i++) {
                System.out.println(controlador.getHabitaciones()[i].toString());
            }

             */
        }
    }

    public void insertarReserva() {
        try {
            System.out.println("Introduce el número de personas");
            int numPersonas = Entrada.entero();
            LocalDate fechaInicio = Consola.leerfecha("Introduce fecha inicio reserva formato (dd/MM/YYYY)");
            LocalDate fechaFin=Consola.leerfecha("Introduce fecha fin reserva formato (dd/MM/YYYY)");
            Huesped huesped = controlador.buscar(Consola.getHuespedPorDni());
            Reserva reserva = new Reserva(huesped,consultarDisponibilidad(Consola.leerTipoHabitacion(),fechaInicio,fechaFin),Consola.leerRegimen(),fechaInicio,fechaFin,numPersonas);
            /*
            Reserva reserva = new Reserva(Consola.getHuespedPorDni(),Consola.leerHabitacionPorIdentificador(),Consola.leerRegimen(),Consola.leerfecha("Introduce fecha inicio reserva formato (dd/MM/YYYY)"),Consola.leerfecha("Introduce fecha fin reserva formato (dd/MM/YYYY)"),numPersonas);

             */
            if(!controlador.getReservas().isEmpty()) {
                controlador.insertar(reserva);
                System.out.println("Reserva insertada correctamente.");
            }else{
                controlador.insertar(reserva);
                System.out.println("Reserva insertada correctamente.");
            }
        }catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException | DateTimeParseException e) {
            System.out.println("-" + e.getMessage());
        }

    }

    public void mostrarReservasHuesped() {
        try {
            listarReservas(Consola.getHuespedPorDni());
        }catch (NullPointerException | IllegalArgumentException e) {
            System.out.println("-" + e.getMessage());
        }
    }

    public static void listarReservas(Huesped huesped) {

        try {
            ArrayList<Reserva> reservasHuesped = controlador.getReservas(huesped);
            if (reservasHuesped.isEmpty()) {
                System.out.println("El huésped no tiene reservas.");
            } else {
                System.out.println("Reservas del hu?sped que has buscado:");
                Comparator<Habitacion> habitacionComparator = Comparator.comparing(Habitacion::getIdentificador);
                Collections.sort(reservasHuesped,Comparator.comparing(Reserva::getFechaInicioReserva).thenComparing(Reserva::getHabitacion,habitacionComparator));
                int contador = 1;
                Iterator<Reserva> reservasHuespedIterador = reservasHuesped.iterator();
                while(reservasHuespedIterador.hasNext()) {
                    Reserva reserva = reservasHuespedIterador.next();
                    System.out.println(contador +".- " + reserva.toString());
                    contador++;
                }
            }
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println("-" + e.getMessage());
        }
    }

    public void mostrarReservasTipoHabitacion() {
        try {
            listarReservas(Consola.leerTipoHabitacion());
        }catch (NullPointerException | IllegalArgumentException | DateTimeParseException e) {
            System.out.println("-" + e.getMessage());
        }

    }

    public void comprobarDisponibilidad() {
        try {
            TipoHabitacion tipoHabitacion = Consola.leerTipoHabitacion();
            LocalDate fechaInicio = Consola.leerfecha("Introduce fecha inicio reserva formato (dd/MM/YYYY)");
            LocalDate fechaFin = Consola.leerfecha("Introduce fecha fin reserva formato (dd/MM/YYYY)");

            Habitacion habitacion = consultarDisponibilidad(tipoHabitacion,fechaInicio,fechaFin);

            if(habitacion!= null) {
                System.out.println("Se puede insertar la reserva");
            }else {
                System.out.println("La habitación no está disponible.");
            }
        }catch (NullPointerException | IllegalArgumentException | DateTimeParseException e) {
            System.out.println("-" + e.getMessage());
        }


    }

    public static void listarReservas(TipoHabitacion tipoHabitacion) {
        if(tipoHabitacion == null) {
            System.out.println("El tipo de habitación no puede ser nulo");
        }
        try {
            ArrayList<Reserva> reservasTipoHabitacion = controlador.getReservas(tipoHabitacion);
            if (reservasTipoHabitacion.isEmpty()) {
                System.out.println("No hay reservas para el tipo de habitación " + tipoHabitacion + ".");
            } else {
                System.out.println("Reservas de habitaciones tipo " + tipoHabitacion + ":");
                Comparator<Huesped> huespedComparator = Comparator.comparing(Huesped::getNombre);
                Collections.sort(reservasTipoHabitacion,Comparator.comparing(Reserva::getFechaInicioReserva).thenComparing(Reserva::getHuesped,huespedComparator));
                Iterator<Reserva> reservasTipoHabitacionIterador = reservasTipoHabitacion.iterator();
                while(reservasTipoHabitacionIterador.hasNext()) {
                    Reserva reserva = reservasTipoHabitacionIterador.next();
                    System.out.println(reserva.toString());
                }
            }
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println("-" + e.getMessage());
        }

    }

    public static ArrayList<Reserva> getReservasAnulables(ArrayList<Reserva> reservaAAnular) {
        ArrayList<Reserva> reservasAnulables = new ArrayList<>();
        Iterator<Reserva> reservasAnulablesIterador = reservaAAnular.iterator();
        while(reservasAnulablesIterador.hasNext()) {
            Reserva reserva = reservasAnulablesIterador.next();
            if(reserva.getFechaInicioReserva().isAfter(LocalDate.now())) {
                reservasAnulables.add(reserva);
            }
        }
        /*
        int contadorReservasAnulables = 0;

        for (int i = 0; i < reservaAAnular.length; i++) {
            Reserva reserva = reservaAAnular[i];
            if (reserva.getFechaInicioReserva().isAfter(LocalDate.now())) {
                contadorReservasAnulables++;
            }
        }

        Reserva[] reservasAnulables = new Reserva[contadorReservasAnulables];
        int contador = 0;

        for (int i = 0; i < reservasAnulables.length; i++) {
            Reserva reserva = reservaAAnular[i];
            if (reserva.getFechaInicioReserva().isAfter(LocalDate.now())) {
                reservasAnulables[contador++] = reserva;
            }
        }

         */
        return reservasAnulables;
    }

    public static void anularReserva() {
        try {
            Huesped huesped = Consola.getHuespedPorDni();
            ArrayList<Reserva> reservasAnulables = getReservasAnulables(controlador.getReservas(huesped));
            if (reservasAnulables.isEmpty()) {
                System.out.println("El huésped no tiene reservas anulables.");
            }else {
                System.out.println("Seleccione la reserva que desea anular:");
                int contador = 1;
                Iterator<Reserva> reservasAnulablesIterador = reservasAnulables.iterator();
                while(reservasAnulablesIterador.hasNext()) {
                    Reserva reservaAnulable = reservasAnulablesIterador.next();
                    System.out.println(contador + ".- " + reservaAnulable.toString());
                    contador++;
                }
            /*
            for (int i = 0; i < reservasAnulables.length; i++) {
                System.out.println((i + 1) + ".-" + reservasAnulables[i].toString());
            }

             */

                int opcion;
                do {
                    System.out.println("Ingrese el número de la reserva a anular:");
                    opcion = Entrada.entero();
                    if (opcion < 1 || opcion > reservasAnulables.size()) {
                        System.out.println("Opción incorrecta, inténtalo de nuevo.");
                    }
                } while (opcion < 1 || opcion > reservasAnulables.size());


                char respuesta;
                do {
                    System.out.println("¿Está seguro de que desea anular la reserva? (s/n)");
                    respuesta = Character.toLowerCase(Entrada.caracter());
                    if (respuesta != 's' && respuesta != 'n') {
                        System.out.println("Respuesta incorrecta, por favor ingrese 's' o 'n'.");
                    }
                } while (respuesta != 's' && respuesta != 'n');

                if (respuesta == 's') {
                    controlador.borrar(reservasAnulables.get(opcion - 1));
                    System.out.println("Reserva anulada correctamente.");
                } else {
                    System.out.println("Anulación de reserva cancelada.");
                }
            }

        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            System.out.println("-" + e.getMessage());
        }
    }

    public static void mostrarReservas() {
        ArrayList<Reserva> reservasTotales = controlador.getReservas();
        if(reservasTotales.isEmpty()) {
            System.out.println("No hay reservas para mostrar.");
        }else{
            Comparator<Habitacion> habitacionComparator = Comparator.comparing(Habitacion::getIdentificador);
            Collections.sort(reservasTotales,Comparator.comparing(Reserva::getFechaInicioReserva).thenComparing(Reserva::getHabitacion,habitacionComparator));
            Iterator<Reserva> todasReservasIterador = reservasTotales.iterator();
            while(todasReservasIterador.hasNext()) {
                System.out.println(todasReservasIterador.next().toString());
            }

        }

    }


    public static Habitacion consultarDisponibilidad(TipoHabitacion tipoHabitacion, LocalDate fechaInicioReserva, LocalDate fechaFinReserva)
    {
        boolean tipoHabitacionEncontrada=false;
        Habitacion habitacionDisponible=null;
        int numElementos=0;

        ArrayList<Habitacion> habitacionesTipoSolicitado= controlador.getHabitaciones(tipoHabitacion);

        if (habitacionesTipoSolicitado==null)
            return habitacionDisponible;

        for (int i=0; i<habitacionesTipoSolicitado.size() && !tipoHabitacionEncontrada; i++)
        {

            if (habitacionesTipoSolicitado.get(i)!=null)
            {
                ArrayList<Reserva> reservasFuturas = controlador.getReservasFuturas(habitacionesTipoSolicitado.get(i));
                numElementos=reservasFuturas.size();

                if (numElementos == 0)
                {
                    //Si la primera de las habitaciones encontradas del tipo solicitado no tiene reservas en el futuro,
                    // quiere decir que est? disponible.
                    if (habitacionesTipoSolicitado.get(i) instanceof Simple) {
                        habitacionDisponible = new Simple((Simple) habitacionesTipoSolicitado.get(i));
                        tipoHabitacionEncontrada = true;
                    } else if (habitacionesTipoSolicitado.get(i) instanceof Doble) {
                        habitacionDisponible = new Doble((Doble) habitacionesTipoSolicitado.get(i));
                        tipoHabitacionEncontrada = true;
                    } else if (habitacionesTipoSolicitado.get(i) instanceof Triple) {
                        habitacionDisponible = new Triple((Triple) habitacionesTipoSolicitado.get(i));
                        tipoHabitacionEncontrada = true;
                    } else if (habitacionesTipoSolicitado.get(i) instanceof Suite) {
                        habitacionDisponible = new Suite((Suite) habitacionesTipoSolicitado.get(i));
                        tipoHabitacionEncontrada = true;
                    }
                }
                else {

                    //Ordenamos de mayor a menor las reservas futuras encontradas por fecha de fin de la reserva.
                    // Si la fecha de inicio de la reserva es posterior a la mayor de las fechas de fin de las reservas
                    // (la reserva de la posici?n 0), quiere decir que la habitaci?n est? disponible en las fechas indicadas.

                    Collections.sort(reservasFuturas,Comparator.comparing(Reserva::getFechaFinReserva).reversed());

                    /*System.out.println("\n\nMostramos las reservas ordenadas por fecha de inicio de menor a mayor (numelementos="+numElementos+")");
                    mostrar(reservasFuturas);*/
                    for(int j=0; j< reservasFuturas.size(); j++) {
                        if (fechaInicioReserva.isAfter(reservasFuturas.get(j).getFechaFinReserva())) {
                            if (habitacionesTipoSolicitado.get(i) instanceof Simple) {
                                habitacionDisponible = new Simple((Simple) habitacionesTipoSolicitado.get(i));
                                tipoHabitacionEncontrada = true;
                            } else if (habitacionesTipoSolicitado.get(i) instanceof Doble) {
                                habitacionDisponible = new Doble((Doble) habitacionesTipoSolicitado.get(i));
                                tipoHabitacionEncontrada = true;
                            } else if (habitacionesTipoSolicitado.get(i) instanceof Triple) {
                                habitacionDisponible = new Triple((Triple) habitacionesTipoSolicitado.get(i));
                                tipoHabitacionEncontrada = true;
                            } else if (habitacionesTipoSolicitado.get(i) instanceof Suite) {
                                habitacionDisponible = new Suite((Suite) habitacionesTipoSolicitado.get(i));
                                tipoHabitacionEncontrada = true;
                            }
                        }

                        if (!tipoHabitacionEncontrada)
                        {
                            //Ordenamos de menor a mayor las reservas futuras encontradas por fecha de inicio de la reserva.
                            // Si la fecha de fin de la reserva es anterior a la menor de las fechas de inicio de las reservas
                            // (la reserva de la posici?n 0), quiere decir que la habitaci?n est? disponible en las fechas indicadas.

                            Collections.sort(reservasFuturas,Comparator.comparing(Reserva::getFechaInicioReserva));

                        /*System.out.println("\n\nMostramos las reservas ordenadas por fecha de inicio de menor a mayor (numelementos="+numElementos+")");
                        mostrar(reservasFuturas);*/

                            if (fechaFinReserva.isBefore(reservasFuturas.get(j).getFechaInicioReserva())) {
                                if (habitacionesTipoSolicitado.get(i) instanceof Simple) {
                                    habitacionDisponible = new Simple((Simple) habitacionesTipoSolicitado.get(i));
                                    tipoHabitacionEncontrada = true;
                                } else if (habitacionesTipoSolicitado.get(i) instanceof Doble) {
                                    habitacionDisponible = new Doble((Doble) habitacionesTipoSolicitado.get(i));
                                    tipoHabitacionEncontrada = true;
                                } else if (habitacionesTipoSolicitado.get(i) instanceof Triple) {
                                    habitacionDisponible = new Triple((Triple) habitacionesTipoSolicitado.get(i));
                                    tipoHabitacionEncontrada = true;
                                } else if (habitacionesTipoSolicitado.get(i) instanceof Suite) {
                                    habitacionDisponible = new Suite((Suite) habitacionesTipoSolicitado.get(i));
                                    tipoHabitacionEncontrada = true;
                                }
                            }
                        }
                    }


                    //Recorremos el array de reservas futuras para ver si las fechas solicitadas est?n alg?n hueco existente entre las fechas reservadas
                    if (!tipoHabitacionEncontrada)
                    {
                        for(int j=1;j<reservasFuturas.size() && !tipoHabitacionEncontrada;j++)
                        {
                            if (reservasFuturas.get(j)!=null && reservasFuturas.get(j - 1)!=null)
                            {
                                if(fechaInicioReserva.isAfter(reservasFuturas.get(j - 1).getFechaFinReserva()) &&
                                        fechaFinReserva.isBefore(reservasFuturas.get(j).getFechaInicioReserva())) {

                                    if (habitacionesTipoSolicitado.get(i) instanceof Simple) {
                                        habitacionDisponible = new Simple((Simple) habitacionesTipoSolicitado.get(i));
                                        tipoHabitacionEncontrada = true;
                                    } else if (habitacionesTipoSolicitado.get(i) instanceof Doble) {
                                        habitacionDisponible = new Doble((Doble) habitacionesTipoSolicitado.get(i));
                                        tipoHabitacionEncontrada = true;
                                    } else if (habitacionesTipoSolicitado.get(i) instanceof Triple) {
                                        habitacionDisponible = new Triple((Triple) habitacionesTipoSolicitado.get(i));
                                        tipoHabitacionEncontrada = true;
                                    } else if (habitacionesTipoSolicitado.get(i) instanceof Suite) {
                                        habitacionDisponible = new Suite((Suite) habitacionesTipoSolicitado.get(i));
                                        tipoHabitacionEncontrada = true;
                                    }
                                }
                            }
                        }
                    }


                }
            }
        }
        return habitacionDisponible;
    }



    public void realizarCheckIn() {
        try {
            Huesped huesped = Consola.getHuespedPorDni();
            ArrayList<Reserva> reservas = controlador.getReservas(huesped);
            if(reservas.isEmpty()) {
                System.out.println("No hay ninguna reserva, por lo que no se puede hacer el check-in");
            }else {
                listarReservas(huesped);


                int opcion;
                do {
                    System.out.println("Inserte Opción");
                    opcion = Entrada.entero();
                } while (opcion < 1 || opcion > reservas.size());
                Reserva reservaFinal = reservas.get(opcion - 1);
                if (reservaFinal.getCheckIn() == null) {
                    controlador.realizarCheckIn(reservaFinal,Consola.leerFechaHora("Introduce la fecha y hora del check-in formato(dd/MM/yyyy hh:mm:ss)"));
                    System.out.println("Check-In actualizado correctamente.");
                } else {
                    System.out.println("Esta reserva ya tiene realizado el Check-In.");
                }
            }
        }catch (NullPointerException | IllegalArgumentException | DateTimeParseException e) {
            System.out.println("-"+ e.getMessage());
        }

    }

    public void realizarCheckOut() {
        try {
            Huesped huesped = Consola.getHuespedPorDni();
            ArrayList<Reserva> reservas = controlador.getReservas(huesped);
            if(reservas.isEmpty()) {
                System.out.println("No hay ninguna reserva, por lo que no se puede hacer el check-out");
            }else {
                listarReservas(huesped);

                int opcion;
                do {
                    System.out.println("Inserte Opción");
                    opcion = Entrada.entero();
                } while (opcion < 1 || opcion > reservas.size());
                Reserva reservaFinal = reservas.get(opcion - 1);
                if (reservaFinal.getCheckOut() == null) {
                    controlador.realizarCheckOut(reservaFinal,Consola.leerFechaHora("Introduce la fecha y hora del check-out formato(dd/MM/yyyy hh:mm:ss)"));
                } else {
                    System.out.println("Esta reserva ya tiene realizado el Check-Out.");
                }
            }
        }catch(NullPointerException | IllegalArgumentException | DateTimeParseException e) {
            System.out.println("-"+ e.getMessage());
        }

    }

}


