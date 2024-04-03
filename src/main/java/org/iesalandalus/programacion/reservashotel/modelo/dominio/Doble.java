package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public class Doble extends Habitacion{
    private static final int NUM_MAXIMO_PERSONAS = 2;
    static final int MIN_NUM_CAMAS_INDIVIDUALES = 0;
    static final int MAX_NUM_CAMAS_INDIVIDUALES = 2;
    static final int MIN_NUM_CAMAS_DOBLES = 0;
    static final int MAX_NUM_CAMAS_DOBLES = 1;
    private int numCamasIndividuales;
    private int numCamasDobles;

    public Doble(int planta, int puerta, double precio, int numCamasIndividuales, int numCamasDobles) {
        super(planta, puerta, precio);
        setNumCamasIndividuales(numCamasIndividuales);
        setNumCamasDobles(numCamasDobles);
        validaNumcamas();
    }

    public Doble(Doble habitacionDoble) {
        super(habitacionDoble);
        setNumCamasIndividuales(habitacionDoble.getNumCamasIndividuales());
        setNumCamasDobles(habitacionDoble.getNumCamasDobles());
    }

    public int getNumCamasIndividuales() {
        return numCamasIndividuales;
    }

    public void setNumCamasIndividuales(int numCamasIndividuales) {
        this.numCamasIndividuales = numCamasIndividuales;
    }

    public int getNumCamasDobles() {
        return numCamasDobles;
    }

    public void setNumCamasDobles(int numCamasDobles) {
        this.numCamasDobles = numCamasDobles;
    }

    private void validaNumcamas() {
        if(getNumCamasIndividuales() < MIN_NUM_CAMAS_INDIVIDUALES || getNumCamasIndividuales() > MAX_NUM_CAMAS_INDIVIDUALES) {
            throw new IllegalArgumentException("ERROR: El n�mero de camas individuales de una habitaci�n doble no puede ser inferior a " + MIN_NUM_CAMAS_INDIVIDUALES +  " ni mayor que " + MAX_NUM_CAMAS_INDIVIDUALES);
        }
        if(getNumCamasDobles() < MIN_NUM_CAMAS_DOBLES || getNumCamasDobles() > MAX_NUM_CAMAS_DOBLES) {
            throw new IllegalArgumentException("ERROR: El n�mero de camas dobles de una habitaci�n doble no puede ser inferior a " + MIN_NUM_CAMAS_DOBLES + " ni mayor que " + MAX_NUM_CAMAS_DOBLES);
        }
        if(getNumCamasIndividuales() == 2 && getNumCamasDobles() == 1 || getNumCamasIndividuales()== 1 && getNumCamasDobles() == 0 || getNumCamasIndividuales() == 1 && getNumCamasDobles() == 1) {
            throw new IllegalArgumentException("ERROR: La distribuci�n de camas en una habitaci�n doble tiene que ser 2 camas individuales y 0 doble o 0 camas individuales y 1 doble");
        }

    }

    @Override
    public int getNumeroMaximoPersonas() {
        return NUM_MAXIMO_PERSONAS;
    }

    @Override
    public String toString() {
        return String.format(super.toString() + ", habitaci�n doble, capacidad=%d personas, camas individuales=%d, camas dobles=%d",getNumeroMaximoPersonas(),getNumCamasIndividuales(),getNumCamasDobles());
    }
}
