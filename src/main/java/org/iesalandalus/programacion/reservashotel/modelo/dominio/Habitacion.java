package org.iesalandalus.programacion.reservashotel.modelo.dominio;

import java.util.Objects;

public abstract class Habitacion {
    public static final double MIN_PRECIO_HABITACION = 40;
    public static final double MAX_PRECIO_HABITACION = 150;
    public static final int MIN_NUMERO_PUERTA = 0;
    public static final int MAX_NUMERO_PUERTA = 15;
    public static final int MIN_NUMERO_PLANTA = 0;
    public static final int MAX_NUMERO_PLANTA = 3;
    protected String identificador;
    protected int planta;
    protected int puerta;
    protected double precio;


    public Habitacion(int planta, int puerta, double precio) {
        setPlanta(planta);
        setPuerta(puerta);
        setIdentificador();
        setPrecio(precio);
    }


    public Habitacion(Habitacion habitacion) {
        if(habitacion == null) {
            throw new NullPointerException("ERROR: No es posible copiar una habitación nula.");
        }
        setPlanta(habitacion.getPlanta());
        setPuerta(habitacion.getPuerta());
        setPrecio(habitacion.getPrecio());
        setIdentificador(habitacion.getIdentificador());
    }

    public abstract int getNumeroMaximoPersonas();



    public String getIdentificador() {
        return identificador;
    }
    protected void setIdentificador() {
        this.identificador = (this.planta) + String.valueOf(this.puerta);
    }
    protected void setIdentificador (String identificador) {
        if(identificador == null) {
            throw new NullPointerException("ERROR: El identificador no puede ser nulo.");
        }
        if(identificador.isBlank()) {
            throw new IllegalArgumentException("ERROR: El identifiacodr no puede ser vacío.");
        }
        this.identificador = identificador;
    }
    public int getPlanta() {
        return planta;
    }
    protected void setPlanta(int planta) {
        if(planta <= MIN_NUMERO_PLANTA || planta > MAX_NUMERO_PLANTA) {
            throw new IllegalArgumentException("ERROR: No se puede establecer como planta de una habitación un valor menor que " + MIN_NUMERO_PLANTA + " ni mayor que " + MAX_NUMERO_PLANTA + ".");
        }
        this.planta = planta;
    }
    public int getPuerta() {
        return puerta;
    }
    protected void setPuerta(int puerta) {
        if(puerta < MIN_NUMERO_PUERTA || puerta >= MAX_NUMERO_PUERTA) {
            throw new IllegalArgumentException("ERROR: No se puede establecer como puerta de una habitación un valor menor que " + MIN_NUMERO_PUERTA + " ni mayor que " + MAX_NUMERO_PUERTA + ".");
        }
        this.puerta = puerta;
    }
    public double getPrecio() {
        return precio;
    }
    protected void setPrecio(double precio) {

        if(precio < MIN_PRECIO_HABITACION || precio > MAX_PRECIO_HABITACION) {
            throw new IllegalArgumentException("ERROR: No se puede establecer como precio de una habitación un valor menor que " + MIN_PRECIO_HABITACION + " ni mayor que " + MAX_PRECIO_HABITACION + ".");
        }


        this.precio = precio;
    }



    @Override
    public boolean equals(Object obj) {
        Habitacion habitacion = (Habitacion) obj;
        return habitacion.getIdentificador().equals(this.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public String toString() {
        return String.format("identificador=%s (%d-%d), precio habitación=%s",identificador,planta,puerta,precio);
    }

}
