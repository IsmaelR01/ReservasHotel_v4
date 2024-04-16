package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Iterator;

import static com.mongodb.client.model.Filters.eq;
import static org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion.SIMPLE;

public class Habitaciones implements IHabitaciones {

    private static final String COLLECCION = "habitaciones";
    private MongoCollection<Document> coleccionHabitaciones;

    public Habitaciones() {
        comenzar();
    }

    public ArrayList<Habitacion> get() {
        ArrayList<Habitacion> copiaHabitaciones = new ArrayList<>();
        FindIterable<Document> copiaHabitacionesIterable = coleccionHabitaciones.find().sort(Sorts.ascending("identificador"));
        Iterator<Document> copiaDocumentHabitacionIterador = copiaHabitacionesIterable.iterator();
        while(copiaDocumentHabitacionIterador.hasNext()) {
            Habitacion habitacion = MongoDB.getHabitacion(copiaDocumentHabitacionIterador.next());

            if(habitacion instanceof Simple) {
                copiaHabitaciones.add(new Simple((Simple) habitacion));
            } else if (habitacion instanceof  Doble) {
                copiaHabitaciones.add(new Doble((Doble) habitacion));
            } else if (habitacion instanceof Triple) {
                copiaHabitaciones.add(new Triple((Triple) habitacion));
            } else if (habitacion instanceof Suite) {
                copiaHabitaciones.add(new Suite((Suite) habitacion));
            }

        }
        return copiaHabitaciones;
    }

    public ArrayList<Habitacion> get(TipoHabitacion tipoHabitacion) {

        ArrayList<Habitacion> listaTipoHabitacion = new ArrayList<>();
        FindIterable<Document> listaTipoHabitacionIterable = coleccionHabitaciones.find();
        Iterator<Document> listaTipoHabitacionDocumentIterador = listaTipoHabitacionIterable.iterator();
        while(listaTipoHabitacionDocumentIterador.hasNext()) {
            Habitacion habitacion = MongoDB.getHabitacion(listaTipoHabitacionDocumentIterador.next());
            switch (tipoHabitacion) {
                case SIMPLE:
                    if(habitacion instanceof Simple) {
                        listaTipoHabitacion.add(new Simple((Simple) habitacion));
                    }
                    break;
                case DOBLE:
                    if(habitacion instanceof Doble) {
                        listaTipoHabitacion.add(new Doble((Doble) habitacion));
                    }
                    break;
                case TRIPLE:
                    if(habitacion instanceof Triple) {
                        listaTipoHabitacion.add(new Triple((Triple) habitacion));
                    }
                    break;
                case SUITE:
                    if(habitacion instanceof Suite) {
                        listaTipoHabitacion.add(new Suite((Suite) habitacion));
                    }

            }

        }
        return listaTipoHabitacion;
    }

    public int getTamano() {
        return (int) coleccionHabitaciones.countDocuments();
    }




    public void insertar(Habitacion habitacion) throws OperationNotSupportedException {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede insertar una habitación nula.");
        }
        if(buscar(habitacion) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe una habitación con ese identificador.");
        } else {
            coleccionHabitaciones.insertOne(MongoDB.getDocumento(habitacion));
        }
    }

    public Habitacion buscar(Habitacion habitacion) {
        if(habitacion == null) {
            throw new NullPointerException("ERROR: No se puede buscar una habitación nula.");
        }
        Document documentoBusquedaHabitacion = coleccionHabitaciones.find().filter(eq(MongoDB.IDENTIFICADOR, habitacion.getIdentificador())).first();
        /*
        if(documentoBusquedaHabitacion != null) {
            if(habitacion instanceof Simple) {
                return new Simple((Simple) MongoDB.getHabitacion(documentoBusquedaHabitacion));
            }
            if(habitacion instanceof Doble) {
                return new Doble((Doble) MongoDB.getHabitacion(documentoBusquedaHabitacion));
            }
            if(habitacion instanceof Triple) {
                return new Triple((Triple) MongoDB.getHabitacion(documentoBusquedaHabitacion));
            }
            if(habitacion instanceof Suite) {
                return new Suite((Suite) MongoDB.getHabitacion(documentoBusquedaHabitacion));
            }
        }

         */
        if(documentoBusquedaHabitacion != null) {
            return MongoDB.getHabitacion(documentoBusquedaHabitacion);
        }
        return null;

    }

    public void borrar(Habitacion habitacion) throws OperationNotSupportedException {

        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede borrar una habitación nula.");
        }

        if (buscar(habitacion) == null) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna habitación como la indicada.");
        } else {
            coleccionHabitaciones.deleteOne(eq(MongoDB.IDENTIFICADOR, habitacion.getIdentificador()));
        }
    }

    public void comenzar() {
        coleccionHabitaciones = MongoDB.getBD().getCollection(COLLECCION);
    }

    public void terminar() {
        MongoDB.cerrarConexion();
    }


}
