package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

public class Reservas implements IReservas {
    private ArrayList<Reserva> coleccionReservas = new ArrayList<>();

    public Reservas() {

    }

    public ArrayList<Reserva> get() {
        ArrayList<Reserva> copiaReservas = new ArrayList<>();
        Iterator<Reserva> copiaReservaIterador = coleccionReservas.iterator();
        while(copiaReservaIterador.hasNext()) {
            Reserva reserva = new Reserva(copiaReservaIterador.next());
            copiaReservas.add(reserva);
        }
        return copiaReservas;
    }


    public int getTamano() {
        return coleccionReservas.size();
    }

    public void insertar(Reserva reserva) throws OperationNotSupportedException {
        if (reserva == null) {
            throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
        }
        if(coleccionReservas.contains(reserva)) {
            throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");
        }
        coleccionReservas.add(reserva);
    }

    public Reserva buscar(Reserva reserva) {
        if(reserva == null) {
            throw new NullPointerException("ERROR: No se puede buscar una reserva nula.");
        }
        if(coleccionReservas.contains(reserva)) {
            Iterator<Reserva> iteradorReserva = coleccionReservas.iterator();
            while(iteradorReserva.hasNext()) {
                if(reserva.equals(iteradorReserva.next())) {
                    return reserva;
                }
            }
        }
        return null;
    }

    public void borrar(Reserva reserva) throws OperationNotSupportedException {

        if (reserva == null) {
            throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
        }

        if (!coleccionReservas.contains(reserva)) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna reserva como la indicada.");
        }
        coleccionReservas.remove(reserva);
    }


    public ArrayList<Reserva> getReservas(Huesped huesped) {
        if(huesped == null) {
            throw  new NullPointerException("ERROR: No se pueden buscar reservas de un huésped nulo.");
        }
        ArrayList<Reserva> listarReservasPorHuesped = new ArrayList<>();
        Iterator<Reserva> listarReservasHuespedIterador = get().iterator();
        while(listarReservasHuespedIterador.hasNext()) {
            Reserva reserva = listarReservasHuespedIterador.next();
            if(reserva.getHuesped().equals(huesped)) {
                listarReservasPorHuesped.add(reserva);
            }
        }
        /*
        for(int i = 0; i< get().size(); i++) {
            if(coleccionReservas.get(i).getHuesped().equals(huesped)) {
                listarReservasPorHuesped.add(coleccionReservas.get(i));
            }
        }

         */
        return listarReservasPorHuesped;
    }

    public ArrayList<Reserva> getReservas(TipoHabitacion tipoHabitacion) {
        if(tipoHabitacion == null) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un tipo de habitación nula.");
        }

        ArrayList<Reserva> listarReservasPorTipoHabitacion = new ArrayList<>();
        Iterator<Reserva> listarReservasTipoHabitacionIterador = get().iterator();
        while(listarReservasTipoHabitacionIterador.hasNext()) {
            Reserva reserva = listarReservasTipoHabitacionIterador.next();
            switch (tipoHabitacion) {
                case SIMPLE:
                    if(reserva.getHabitacion() instanceof Simple) {
                        listarReservasPorTipoHabitacion.add(new Reserva(reserva));
                    }
                    break;
                case DOBLE:
                    if(reserva.getHabitacion() instanceof Doble) {
                        listarReservasPorTipoHabitacion.add(new Reserva(reserva));
                    }
                    break;
                case TRIPLE:
                    if(reserva.getHabitacion() instanceof Triple) {
                        listarReservasPorTipoHabitacion.add(new Reserva(reserva));
                    }
                    break;
                case SUITE:
                    if(reserva.getHabitacion() instanceof Suite) {
                        listarReservasPorTipoHabitacion.add(new Reserva(reserva));
                    }

            }

        }
        return listarReservasPorTipoHabitacion;
    }

    public ArrayList<Reserva> getReservasFuturas(Habitacion habitacion) {
        if(habitacion == null) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitación nula.");
        }

        ArrayList<Reserva> reservasFuturas = new ArrayList<>();
        Iterator<Reserva> reservasFuturasIterador = get().iterator();
        while(reservasFuturasIterador.hasNext()) {
            Reserva reserva = reservasFuturasIterador.next();
            if(reserva.getHabitacion().equals(habitacion) && reserva.getFechaInicioReserva().isAfter(LocalDate.now())) {
                reservasFuturas.add(reserva);
            }
        }
        /*
        for (int i = 0; i < get().size(); i++) {
            if (coleccionReservas.get(i).getHabitacion().equals(habitacion) &&
                    coleccionReservas.get(i).getFechaInicioReserva().isAfter(LocalDate.now())) {
                reservasFuturas.add(coleccionReservas.get(i));
            }
        }

         */
        return reservasFuturas;
    }

    public void realizarCheckIn(Reserva reserva, LocalDateTime fecha) {
        Iterator<Reserva> reservaCheckInIterador = coleccionReservas.iterator();
        while(reservaCheckInIterador.hasNext()) {
            Reserva reservaCheckIn = reservaCheckInIterador.next();
            if(reservaCheckIn != null) {
                if(reservaCheckIn.equals(reserva)) {
                    reservaCheckIn.setCheckIn(fecha);
                }
            }
        }
        /*
        for (int i = 0; i < coleccionReservas.size(); i++) {
            Reserva reservaCheckIn = coleccionReservas.get(i);
            if (reservaCheckIn != null) {
                if (reservaCheckIn.equals(reserva)) {
                    reservaCheckIn.setCheckIn(fecha);
                }
            }
        }

         */
    }

    public void realizarCheckOut(Reserva reserva, LocalDateTime fecha) {
        Iterator<Reserva> reservaCheckOutIterador = coleccionReservas.iterator();
        while(reservaCheckOutIterador.hasNext()) {
            Reserva reservaCheckOut = reservaCheckOutIterador.next();
            if(reservaCheckOut != null) {
                if(reservaCheckOut.equals(reserva)) {
                    if(reserva.getCheckIn()!= null) {
                        reservaCheckOut.setCheckOut(fecha);
                        System.out.println("Check-Out actualizado correctamente.");
                    }else {
                        System.out.println("Primero debes realizar el Check-In");
                    }
                }
            }
        }
        /*
        Iterator<Reserva> reservaCheckOutIterador = coleccionReservas.iterator();
        while(reservaCheckOutIterador.hasNext()) {
            Reserva reservaCheckOut = reservaCheckOutIterador.next();
            if(reservaCheckOut.getCheckIn() != null) {
                if(reservaCheckOut != null) {
                    if(reservaCheckOut.equals(reserva)) {
                        reservaCheckOut.setCheckOut(fecha);
                        System.out.println("Check-Out actualizado correctamente.");
                    }
                }
            }else {
                System.out.println("Primero debes realizar el Check-In");
            }

        }

         */

    }



}


