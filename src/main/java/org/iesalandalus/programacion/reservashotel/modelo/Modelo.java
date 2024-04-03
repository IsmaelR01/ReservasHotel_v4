package org.iesalandalus.programacion.reservashotel.modelo;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria.Habitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria.Huespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria.Reservas;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Modelo {
    private static IHuespedes huespedes;
    private static IHabitaciones habitaciones;
    private static IReservas reservas;
    public Modelo() {

    }

    public void comenzar() {
         huespedes = new Huespedes();
         habitaciones = new Habitaciones();
         reservas = new Reservas();
    }

    public void terminar() {
        System.out.println("���Has terminado, Hasta la pr�xima!!!");
    }

    public void insertar(Huesped huesped) throws OperationNotSupportedException {
        huespedes.insertar(huesped);
    }

    public Huesped buscar(Huesped huesped) {
        return huespedes.buscar(huesped);
    }

    public void borrar(Huesped huesped) throws OperationNotSupportedException {
        huespedes.borrar(huesped);
    }

    public ArrayList<Huesped> getHuespedes() {
        return huespedes.get();
    }

    public void insertar(Habitacion habitacion) throws OperationNotSupportedException {
        habitaciones.insertar(habitacion);
    }

    public Habitacion buscar(Habitacion habitacion) {
        return habitaciones.buscar(habitacion);
    }

    public void borrar(Habitacion habitacion) throws OperationNotSupportedException {
        habitaciones.borrar(habitacion);
    }

    public ArrayList<Habitacion> getHabitaciones() {
        return habitaciones.get();
    }
    public ArrayList<Habitacion> getHabitaciones(TipoHabitacion tipoHabitacion) {
        return habitaciones.get(tipoHabitacion);
    }

    public void insertar(Reserva reserva) throws OperationNotSupportedException {
        reservas.insertar(reserva);
    }

    public void borrar(Reserva reserva) throws OperationNotSupportedException {
        reservas.borrar(reserva);
    }

    public Reserva buscar(Reserva reserva) {
        return reservas.buscar(reserva);
    }

    public ArrayList<Reserva> getReservas() {
        return reservas.get();
    }

    public ArrayList<Reserva> getReservas(Huesped huesped) {
        return reservas.getReservas(huesped);
    }

    public ArrayList<Reserva> getReservas(TipoHabitacion tipoHabitacion) {
        return reservas.getReservas(tipoHabitacion);
    }

    public ArrayList<Reserva> getReservasFuturas(Habitacion habitacion) {
        return reservas.getReservasFuturas(habitacion);
    }

    public void realizarCheckIn(Reserva reserva, LocalDateTime fecha) {
        reservas.realizarCheckIn(reserva,fecha);
    }

    public void realizarCheckOut(Reserva reserva, LocalDateTime fecha) {
        reservas.realizarCheckOut(reserva,fecha);
    }

}


