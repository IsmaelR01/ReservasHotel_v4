package org.iesalandalus.programacion.reservashotel.modelo;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IFuenteDatos;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface IModelo {
     void comenzar();

     void terminar();

     void insertar(Huesped huesped) throws OperationNotSupportedException;

     Huesped buscar(Huesped huesped);

     void borrar(Huesped huesped) throws OperationNotSupportedException;

     ArrayList<Huesped> getHuespedes();

     void insertar(Habitacion habitacion) throws OperationNotSupportedException;

     Habitacion buscar(Habitacion habitacion);

     void borrar(Habitacion habitacion) throws OperationNotSupportedException;

     ArrayList<Habitacion> getHabitaciones();
     ArrayList<Habitacion> getHabitaciones(TipoHabitacion tipoHabitacion);

     void insertar(Reserva reserva) throws OperationNotSupportedException;

     void borrar(Reserva reserva) throws OperationNotSupportedException;

     Reserva buscar(Reserva reserva);

     ArrayList<Reserva> getReservas();

     ArrayList<Reserva> getReservas(Huesped huesped);

     ArrayList<Reserva> getReservas(TipoHabitacion tipoHabitacion);

     ArrayList<Reserva> getReservas(Habitacion habitacion);

     ArrayList<Reserva> getReservasFuturas(Habitacion habitacion);


     void realizarCheckIn(Reserva reserva, LocalDateTime fecha);



     void realizarCheckOut(Reserva reserva, LocalDateTime fecha);




}
