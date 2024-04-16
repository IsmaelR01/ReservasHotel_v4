package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;

import javax.naming.OperationNotSupportedException;
import org.bson.Document;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class Reservas implements IReservas {

    private static final String COLLECCION = "reservas";
    private MongoCollection<Document> coleccionReservas;

    public Reservas() {
        comenzar();
    }

    public ArrayList<Reserva> get() {
        ArrayList<Reserva> copiaReservas = new ArrayList<>();
        FindIterable<Document> copiaReservasIterable = coleccionReservas.find().sort(Sorts.ascending(MongoDB.FECHA_INICIO_RESERVA));
        Iterator<Document> copiaReservaDocumentIterador = copiaReservasIterable.iterator();
        while(copiaReservaDocumentIterador.hasNext()) {
            Document documentoReserva = copiaReservaDocumentIterador.next();
            Reserva reserva = MongoDB.getReserva(documentoReserva);
            if(!documentoReserva.getString(MongoDB.CHECKIN).equals("No Registrado")) {
                LocalDateTime fechaCheckIn = LocalDateTime.parse(documentoReserva.getString("checkin"),MongoDB.FORMATO_DIA_HORA);
                reserva.setCheckIn(fechaCheckIn);
            }
            if(!documentoReserva.getString(MongoDB.CHECKOUT).equals("No Registrado")) {
                LocalDateTime fechaCheckOut = LocalDateTime.parse(documentoReserva.getString("checkout"),MongoDB.FORMATO_DIA_HORA);
                reserva.setCheckOut(fechaCheckOut);
            }
            copiaReservas.add(reserva);
        }
        return copiaReservas;
    }


    public int getTamano() {
        return (int) coleccionReservas.countDocuments();
    }

    public void insertar(Reserva reserva) throws OperationNotSupportedException {
        if (reserva == null) {
            throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
        }
        if(buscar(reserva) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");
        } else {
            coleccionReservas.insertOne(MongoDB.getDocumento(reserva));
        }
    }

    public Reserva buscar(Reserva reserva) {
        if(reserva == null) {
            throw new NullPointerException("ERROR: No se puede buscar una reserva nula.");
        }
        Document documentoBusquedaReserva = coleccionReservas.find().filter(and(
                eq(MongoDB.HUESPED_DNI, reserva.getHuesped().getDni()),
                eq(MongoDB.HABITACION_IDENTIFICADOR, reserva.getHabitacion().getIdentificador())
        )).first();
        if(documentoBusquedaReserva !=null) {
            return new Reserva(MongoDB.getReserva(documentoBusquedaReserva));
        }
        return null;
    }

    public void borrar(Reserva reserva) throws OperationNotSupportedException {

        if (reserva == null) {
            throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
        }

        if (buscar(reserva) == null) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna reserva como la indicada.");
        } else {
            coleccionReservas.deleteOne(MongoDB.getDocumento(reserva));
        }
    }


    public ArrayList<Reserva> getReservas(Huesped huesped) {
        if(huesped == null) {
            throw  new NullPointerException("ERROR: No se pueden buscar reservas de un huésped nulo.");
        }
        ArrayList<Reserva> listarReservasPorHuesped = new ArrayList<>();
        FindIterable<Document> listarReservaIterable = coleccionReservas.find().sort(Sorts.ascending(MongoDB.FECHA_INICIO_RESERVA));
        Iterator<Document> listarReservasHuespedDocumentIterador = listarReservaIterable.iterator();
        while(listarReservasHuespedDocumentIterador.hasNext()) {
            Document documentoReserva = listarReservasHuespedDocumentIterador.next();
            Reserva reserva = MongoDB.getReserva(documentoReserva);
            if(reserva.getHuesped().equals(huesped)) {
                if(!documentoReserva.getString(MongoDB.CHECKIN).equals("No Registrado")) {
                    LocalDateTime fechaCheckIn = LocalDateTime.parse(documentoReserva.getString("checkin"),MongoDB.FORMATO_DIA_HORA);
                    reserva.setCheckIn(fechaCheckIn);
                }
                if(!documentoReserva.getString(MongoDB.CHECKOUT).equals("No Registrado")) {
                    LocalDateTime fechaCheckOut = LocalDateTime.parse(documentoReserva.getString("checkout"),MongoDB.FORMATO_DIA_HORA);
                    reserva.setCheckOut(fechaCheckOut);
                }
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
        FindIterable<Document> listarReservasIterable = coleccionReservas.find().sort(Sorts.ascending(MongoDB.IDENTIFICADOR));
        Iterator<Document> listarReservasTipoHabitacionDocumentIterador = listarReservasIterable.iterator();
        while(listarReservasTipoHabitacionDocumentIterador.hasNext()) {
            Document documentoReserva = listarReservasTipoHabitacionDocumentIterador.next();
            Reserva reserva = MongoDB.getReserva(documentoReserva);
            if(!documentoReserva.getString(MongoDB.CHECKIN).equals("No Registrado")) {
                LocalDateTime fechaCheckIn = LocalDateTime.parse(documentoReserva.getString("checkin"),MongoDB.FORMATO_DIA_HORA);
                reserva.setCheckIn(fechaCheckIn);
            }
            if(!documentoReserva.getString(MongoDB.CHECKOUT).equals("No Registrado")) {
                LocalDateTime fechaCheckOut = LocalDateTime.parse(documentoReserva.getString("checkout"),MongoDB.FORMATO_DIA_HORA);
                reserva.setCheckOut(fechaCheckOut);
            }
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

    public ArrayList<Reserva> getReservas(Habitacion habitacion) {
        if(habitacion == null) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitación nula.");
        }

        ArrayList<Reserva> reservasHabitacion = new ArrayList<>();
        FindIterable<Document> reservasIterable = coleccionReservas.find().sort(Sorts.ascending(MongoDB.FECHA_INICIO_RESERVA));
        Iterator<Document> reservasIterador = reservasIterable.iterator();
        while(reservasIterador.hasNext()) {
            Document documentoReserva = reservasIterador.next();
            Reserva reserva = MongoDB.getReserva(documentoReserva);
            if(reserva.getHabitacion().equals(habitacion)) {
                reservasHabitacion.add(reserva);
            }
        }
        return reservasHabitacion;
    }

    public ArrayList<Reserva> getReservasFuturas(Habitacion habitacion) {
        if(habitacion == null) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitación nula.");
        }

        ArrayList<Reserva> reservasFuturas = new ArrayList<>();
        FindIterable<Document> reservasFuturasIterable = coleccionReservas.find().sort(Sorts.ascending(MongoDB.FECHA_INICIO_RESERVA));
        Iterator<Document> reservasFuturasIterador = reservasFuturasIterable.iterator();
        while(reservasFuturasIterador.hasNext()) {
            Document documentoReserva = reservasFuturasIterador.next();
            Reserva reserva = MongoDB.getReserva(documentoReserva);
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
        if(reserva == null) {
            throw new NullPointerException("ERROR: La reserva no puede ser nula.");
        }
        if(fecha == null) {
            throw new NullPointerException("ERROR: La fecha no puede ser nula");
        }

        FindIterable<Document> documentReservaCheckInIterable = coleccionReservas.find();
        Iterator<Document> reservaCheckInDocumentIterador = documentReservaCheckInIterable.iterator();
        while(reservaCheckInDocumentIterador.hasNext()) {
            Document documentReservaCheckIn = reservaCheckInDocumentIterador.next();
            Reserva reservaCheckIn = MongoDB.getReserva(documentReservaCheckIn);
            if(reservaCheckIn.equals(reserva)) {
                reservaCheckIn.setCheckIn(fecha);
                coleccionReservas.updateOne(Filters.eq(MongoDB.CHECKIN, documentReservaCheckIn.getString(MongoDB.CHECKIN)), Updates.set(MongoDB.CHECKIN, fecha.format(MongoDB.FORMATO_DIA_HORA)));
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
        if(reserva == null) {
            throw new NullPointerException("ERROR: La reserva no puede ser nula.");
        }
        if(fecha == null) {
            throw new NullPointerException("ERROR: La fecha no puede ser nula");
        }
        FindIterable<Document> documentReservaCheckInIterable = coleccionReservas.find();
        Iterator<Document> reservaCheckOutDocumentIterador = documentReservaCheckInIterable.iterator();
        while(reservaCheckOutDocumentIterador.hasNext()) {
            Document documentReservaCheckOut = reservaCheckOutDocumentIterador.next();
            Reserva reservaCheckOut = MongoDB.getReserva(documentReservaCheckOut);

            if(reservaCheckOut.equals(reserva)) {
                if(reserva.getCheckIn()!= null) {

                    /*
                    coleccionReservas.updateOne(Filters.eq(MongoDB.CHECKOUT, documentReservaCheckOut.getString(MongoDB.CHECKOUT)), Updates.set(MongoDB.CHECKOUT, String.format(fecha.toString(), MongoDB.FORMATO_DIA_HORA)));
                     */
                    reserva.setCheckOut(fecha);
                    coleccionReservas.updateOne(Filters.eq(MongoDB.CHECKOUT, documentReservaCheckOut.getString(MongoDB.CHECKOUT)), Updates.set(MongoDB.CHECKOUT, fecha.format(MongoDB.FORMATO_DIA_HORA)));
                    coleccionReservas.updateOne(Filters.eq(MongoDB.PRECIO_RESERVA, documentReservaCheckOut.getDouble(MongoDB.PRECIO_RESERVA)), Updates.set(MongoDB.PRECIO_RESERVA, reserva.getPrecio()));

                    System.out.println("Check-Out actualizado correctamente.");
                }else {
                    System.out.println("Primero debes realizar el Check-In");
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

    public void comenzar() {
        coleccionReservas = MongoDB.getBD().getCollection(COLLECCION);
    }

    public void terminar() {
        MongoDB.cerrarConexion();
    }



}


