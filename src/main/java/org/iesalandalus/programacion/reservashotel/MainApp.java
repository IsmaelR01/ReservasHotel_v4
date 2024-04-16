package org.iesalandalus.programacion.reservashotel;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.IModelo;
import org.iesalandalus.programacion.reservashotel.modelo.Modelo;

import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;
import org.iesalandalus.programacion.reservashotel.vista.Vista;

import javax.naming.OperationNotSupportedException;
import java.time.format.DateTimeParseException;


public class MainApp {


    public static void main(String[] args) {
        try {
            Modelo modelo = new Modelo(procesarArgumentosFuenteDatos(args));
            Vista vista = new Vista();
            Controlador controlador = new Controlador(modelo,vista);
            controlador.comenzar();
            controlador.terminar();

        }catch (NullPointerException | IllegalArgumentException |  DateTimeParseException e) {
            System.out.println("-" + e.getMessage());
        }

    }

    private static FactoriaFuenteDatos procesarArgumentosFuenteDatos(String[] parametros) {
        for(String argumento : parametros) {
            if(argumento.equals("-fdmongodb")) {
                return FactoriaFuenteDatos.MONGODB;
            }
            if(argumento.equals("-fdmemoria")) {
                return FactoriaFuenteDatos.MEMORIA;
            }
        }
        return FactoriaFuenteDatos.MONGODB;
    }

}



