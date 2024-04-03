package org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Iterator;

import static org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion.SIMPLE;

public class Habitaciones implements IHabitaciones {
    private ArrayList<Habitacion> coleccionHabitaciones = new ArrayList<>();

    public Habitaciones() {

    }

    public ArrayList<Habitacion> get() {
        ArrayList<Habitacion> copiaHabitaciones = new ArrayList<>();
        Iterator<Habitacion> copiaHabitacionIterador = coleccionHabitaciones.iterator();
        while(copiaHabitacionIterador.hasNext()) {
            Habitacion habitacion = copiaHabitacionIterador.next();
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
        Iterator<Habitacion> listaTipoHabitacionIterador = get().iterator();
        while(listaTipoHabitacionIterador.hasNext()) {
            Habitacion habitacion = listaTipoHabitacionIterador.next();
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




    public void insertar(Habitacion habitacion) throws OperationNotSupportedException {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede insertar una habitación nula.");
        }
        if(coleccionHabitaciones.contains(habitacion)) {
            throw new OperationNotSupportedException("ERROR: Ya existe una habitación con ese identificador.");
        }
        coleccionHabitaciones.add(habitacion);
    }

    public Habitacion buscar(Habitacion habitacion) {
        if(habitacion == null) {
            throw new NullPointerException("ERROR: No se puede buscar una habitación nula.");
        }
        int indice = coleccionHabitaciones.indexOf(habitacion);
        if(indice == -1) {
            return null;
        }else {
            return coleccionHabitaciones.get(indice);
        }
    }

    public void borrar(Habitacion habitacion) throws OperationNotSupportedException {

        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede borrar una habitación nula.");
        }

        if (!coleccionHabitaciones.contains(habitacion)) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna habitación como la indicada.");
        }
        coleccionHabitaciones.remove(habitacion);
    }

    public int getTamano() {
        return coleccionHabitaciones.size();
    }

    public void comenzar() {

    }

    public void terminar() {

    }
}
