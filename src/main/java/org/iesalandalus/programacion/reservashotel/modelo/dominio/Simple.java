package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public class Simple extends Habitacion {
    private static final int NUM_MAXIMO_PERSONAS = 1;

    public Simple(int planta, int puerta, double precio) {
        super(planta, puerta, precio);
    }
    public Simple(Simple habitacionSimple) {
        super(habitacionSimple);
    }

    @Override
    public int getNumeroMaximoPersonas() {
        return NUM_MAXIMO_PERSONAS;
    }

    @Override
    public String toString() {
        return String.format(super.toString() + ", habitación simple, capacidad=%d personas",getNumeroMaximoPersonas()) ;
    }
}
