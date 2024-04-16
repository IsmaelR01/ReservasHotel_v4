package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;


import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import static com.mongodb.client.model.Filters.eq;

public class Huespedes implements IHuespedes {

    private static final String COLECCION = "huespedes";
    private MongoCollection<Document> coleccionHuespedes;

    public Huespedes() {
        comenzar();
    }

    public ArrayList<Huesped> get() {
        /*
        ArrayList<Huesped> copiaHuespedes = new ArrayList<>();
        Iterator<Huesped> copiaHuespedIterador = coleccionHuespedes.iterator();
        while(copiaHuespedIterador.hasNext()) {
            Huesped huesped = new Huesped(copiaHuespedIterador.next());
            copiaHuespedes.add(huesped);
        }
        return copiaHuespedes;
         */
        ArrayList<Huesped> copiaHuesped = new ArrayList<>();

        FindIterable<Document> copiaHuespedIterable = coleccionHuespedes.find().sort(Sorts.ascending("dni"));
        Iterator<Document> copiaDocumentHuespedIterator = copiaHuespedIterable.iterator();
        while (copiaDocumentHuespedIterator.hasNext()) {
            copiaHuesped.add(new Huesped(MongoDB.getHuesped(copiaDocumentHuespedIterator.next())));
        }
        return copiaHuesped;
    }


    public int getTamano() {
        return (int) coleccionHuespedes.countDocuments();
        /*
        coleccionHuespedes.size();

         */
    }

    public void insertar(Huesped huesped) throws OperationNotSupportedException {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede insertar un huésped nulo.");
        }
        if(buscar(huesped) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe un huésped con ese dni.");
        } else {
            coleccionHuespedes.insertOne(MongoDB.getDocumento(huesped));
        }
    }

    public Huesped buscar(Huesped huesped) {
        if(huesped == null) {
            throw new NullPointerException("ERROR: No se puede buscar un huésped nulo.");
        }
        Document documentoBusquedaHuesped = coleccionHuespedes.find().filter(eq(MongoDB.DNI, huesped.getDni())).first();
        if(documentoBusquedaHuesped != null) {
            return new Huesped(MongoDB.getHuesped(documentoBusquedaHuesped));
        } else {
            return null;
        }

    }

    public void borrar(Huesped huesped) throws OperationNotSupportedException {

        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede borrar un huésped nulo.");
        }

        if (buscar(huesped) == null) {
            throw new OperationNotSupportedException("ERROR: No existe ningún huésped como el indicado.");
        } else  {
            coleccionHuespedes.deleteOne(eq(MongoDB.DNI, huesped.getDni()));
        }
    }

    public void comenzar() {
        coleccionHuespedes = MongoDB.getBD().getCollection(COLECCION);
    }

    public void terminar() {
        MongoDB.cerrarConexion();
    }


}

