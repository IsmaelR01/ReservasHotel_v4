package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public class Suite extends Habitacion {
    private static final int NUM_MAXIMO_PERSONAS = 4;
    static final int MIN_NUM_BANOS = 1;
    static final int MAX_NUM_BANOS = 2;
    private int  numBanos;
    private boolean tieneJacuzzi;

    public Suite(int planta, int puerta, double precio, int numBanos, boolean tieneJacuzzi) {
        super(planta, puerta, precio);
        setNumBanos(numBanos);
        setTieneJacuzzi(tieneJacuzzi);
    }

    public Suite(Suite habitacionSuite) {
        super(habitacionSuite);
        setNumBanos(habitacionSuite.getNumBanos());
        setTieneJacuzzi(habitacionSuite.isTieneJacuzzi());
    }

    public int getNumBanos() {
        return numBanos;
    }

    public void setNumBanos(int numBanos) {
        if(numBanos < MIN_NUM_BANOS || numBanos > MAX_NUM_BANOS) {
            throw new IllegalArgumentException("ERROR: El n�mero de ba�os no puede ser inferior a " + MIN_NUM_BANOS + " ni superior a " + MAX_NUM_BANOS);
        }
        this.numBanos = numBanos;
    }

    public boolean isTieneJacuzzi() {
        return tieneJacuzzi;
    }

    public void setTieneJacuzzi(boolean tieneJacuzzi) {
        this.tieneJacuzzi = tieneJacuzzi;
    }

    @Override
    public int getNumeroMaximoPersonas() {
        return NUM_MAXIMO_PERSONAS;
    }

    @Override
    public String toString() {
        if(isTieneJacuzzi()) {
            return String.format(super.toString() + ", habitaci�n suite, capacidad=%d personas, ba�os=%d, con Jacuzzi",getNumeroMaximoPersonas(),getNumBanos());
        }else {
            return String.format(super.toString() + ", habitaci�n suite, capacidad=%d personas, ba�os=%d, sin Jacuzzi",getNumeroMaximoPersonas(),getNumBanos());
        }

    }
}
