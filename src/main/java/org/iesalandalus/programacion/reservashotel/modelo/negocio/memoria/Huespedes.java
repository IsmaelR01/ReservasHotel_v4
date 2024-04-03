package org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;


import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Iterator;

public class Huespedes implements IHuespedes {
    private ArrayList<Huesped> coleccionHuespedes = new ArrayList<>();

    public Huespedes() {

    }

    public ArrayList<Huesped> get() {
        ArrayList<Huesped> copiaHuespedes = new ArrayList<>();
        Iterator<Huesped> copiaHuespedIterador = coleccionHuespedes.iterator();
        while(copiaHuespedIterador.hasNext()) {
            Huesped huesped = new Huesped(copiaHuespedIterador.next());
            copiaHuespedes.add(huesped);
        }
        return copiaHuespedes;
    }


    public int getTamano() {
        return coleccionHuespedes.size();
    }

    public void insertar(Huesped huesped) throws OperationNotSupportedException {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede insertar un huésped nulo.");
        }
        if(coleccionHuespedes.contains(huesped)) {
            throw new OperationNotSupportedException("ERROR: Ya existe un huésped con ese dni.");
        }
        coleccionHuespedes.add(huesped);
    }

    public Huesped buscar(Huesped huesped) {
        if(huesped == null) {
            throw new NullPointerException("ERROR: No se puede buscar un huésped nulo.");
        }
        int indice = coleccionHuespedes.indexOf(huesped);
        if(indice == -1) {
            return null;
        }else {
            return coleccionHuespedes.get(indice);
        }
    }

    public void borrar(Huesped huesped) throws OperationNotSupportedException {

        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede borrar un huésped nulo.");
        }

        if (!coleccionHuespedes.contains(huesped)) {
            throw new OperationNotSupportedException("ERROR: No existe ningún huésped como el indicado.");
        }
        coleccionHuespedes.remove(huesped);
    }

    public void comenzar() {

    }

    public void terminar() {

    }


}

