package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;

import org.bson.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.mongodb.client.model.Filters.eq;

public class MongoDB {
    public static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static final DateTimeFormatter FORMATO_DIA_HORA = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private static final int PUERTO = 27017;

    private static final String BD = "reservashotel";

    private static final String USUARIO = "reservashotel";

    private static final String CONTRASENA = "reservashotel-2024";

    private static final String SERVIDOR =  "cluster0.5r48hux.mongodb.net";

    public static final String HUESPED = "huesped";

    public static final String NOMBRE = "nombre";

    public static final String DNI = "dni";

    public static final String TELEFONO = "telefono";

    public static final String CORREO = "correo";

    public static final String FECHA_NACIMIENTO = "fecha_nacimiento";

    public static final String HUESPED_DNI = HUESPED + "." + DNI;

    public static final String HABITACION = "habitacion";

    public static final String IDENTIFICADOR = "identificador";

    public static final String PLANTA = "planta";

    public static final String PUERTA = "puerta";

    public static final String PRECIO="precio";

    public static final String HABITACION_IDENTIFICADOR = HABITACION + "." + IDENTIFICADOR;

    public static final String TIPO = "tipo";

    public static final String HABITACION_TIPO=HABITACION + "." + TIPO;

    public static final String TIPO_SIMPLE="SIMPLE";

    public static final String TIPO_DOBLE="DOBLE";

    public static final String TIPO_TRIPLE="TRIPLE";

    public static final String TIPO_SUITE="SUITE";

    public static final String CAMAS_INDIVIDUALES="camas_individuales";

    public static final String CAMAS_DOBLES="camas_dobles";

    public static final String BANOS="banos";

    public static final String JACUZZI="jacuzzi";

    public static final String REGIMEN = "regimen";

    public static final String FECHA_INICIO_RESERVA="fecha_inicio_reserva";

    public static final String FECHA_FIN_RESERVA="fecha_fin_reserva";

    public static final String CHECKIN = "checkin";

    public static final String CHECKOUT = "checkout";

    public static final String PRECIO_RESERVA="precio_reserva";

    public static final String NUMERO_PERSONAS="numero_personas";

    private static MongoClient conexion;

    private MongoDB() {

    }

    public static MongoDatabase getBD() {
        if(conexion == null) {
            establecerConexion();
        }
        return conexion.getDatabase(BD);
    }

    private static void establecerConexion() {
        /*
        conexion = MongoClients.create(new ConnectionString(SERVIDOR));
         */

        String connectionString;
        ServerApi serverApi;
        MongoClientSettings settings;

        if (!SERVIDOR.equals("localhost")) {
            connectionString = "mongodb+srv://"+ USUARIO+ ":" + CONTRASENA + "@"+ SERVIDOR +"/?retryWrites=true&w=majority";
            serverApi = ServerApi.builder()
                    .version(ServerApiVersion.V1)
                    .build();

            settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(connectionString))
                    .serverApi(serverApi)
                    .build();
        } else {
            connectionString="mongodb://" + USUARIO + ":" + CONTRASENA + "@" + SERVIDOR + ":" + PUERTO ;
            MongoCredential credenciales = MongoCredential.createScramSha1Credential(USUARIO, BD, CONTRASENA.toCharArray());

            settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(connectionString))
                    .credential(credenciales)
                    .build();
        }


        //Creamos la conexión con el serveridos según el setting anterior
        conexion = MongoClients.create(settings);

        try {
            if (!SERVIDOR.equals("localhost")) {
                MongoDatabase database = conexion.getDatabase(BD);
                database.runCommand(new Document("ping", 1));
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }
        System.out.println("Conexión a MongoDB realizada correctamente.");
    }

    public static void cerrarConexion() {
        if (conexion != null) {
            conexion.close();
            conexion = null;
            System.out.println("Conexión a MongoDB cerrada.");
        }
    }

    public static Document getDocumento(Huesped huesped) {
        if(huesped == null) {
            throw new NullPointerException("ERROR: El húesped no puede ser nulo.");
        }
        return new Document().append(NOMBRE, huesped.getNombre()).append(DNI, huesped.getDni()).append(TELEFONO, huesped.getTelefono()).append(CORREO,huesped.getCorreo()).append(FECHA_NACIMIENTO, huesped.getFechaNacimiento());
    }

    public static Huesped getHuesped(Document documentoHuesped) {
        if(documentoHuesped == null) {
            throw new NullPointerException("ERROR: El documento de un huésped no puede ser nulo.");
        }
        String fechaNacimientoCadena = documentoHuesped.getString(FECHA_NACIMIENTO);
        LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoCadena,FORMATO_DIA);
        return new Huesped(documentoHuesped.getString(NOMBRE), documentoHuesped.getString(DNI), documentoHuesped.getString(TELEFONO), documentoHuesped.getString(CORREO), fechaNacimiento);
    }

    public static Document getDocumento(Habitacion habitacion) {
        if(habitacion == null) {
            throw new NullPointerException("ERROR: La habitación no puede ser nula.");
        }
        if(habitacion instanceof Simple) {
            return new Document().append(PLANTA, habitacion.getPlanta()).append(PUERTA, habitacion.getPuerta()).append(IDENTIFICADOR, habitacion.getIdentificador()).append(PRECIO, habitacion.getPrecio()).append(TIPO, TIPO_SIMPLE).append(NUMERO_PERSONAS, habitacion.getNumeroMaximoPersonas());
        }
        if(habitacion instanceof Doble) {
            return new Document().append(PLANTA, habitacion.getPlanta()).append(PUERTA, habitacion.getPuerta()).append(IDENTIFICADOR, habitacion.getIdentificador()).append(PRECIO, habitacion.getPrecio()).append(TIPO, TIPO_DOBLE).append(CAMAS_INDIVIDUALES, ((Doble) habitacion).getNumCamasIndividuales()).append(CAMAS_DOBLES, ((Doble) habitacion).getNumCamasDobles()).append(NUMERO_PERSONAS, habitacion.getNumeroMaximoPersonas());
        }
        if(habitacion instanceof Triple) {
            return new Document().append(PLANTA, habitacion.getPlanta()).append(PUERTA, habitacion.getPuerta()).append(IDENTIFICADOR, habitacion.getIdentificador()).append(PRECIO, habitacion.getPrecio()).append(TIPO, TIPO_TRIPLE).append(BANOS, ((Triple) habitacion).getNumBanos()).append(CAMAS_INDIVIDUALES, ((Triple) habitacion).getNumCamasIndividuales()).append(CAMAS_DOBLES, ((Triple) habitacion).getNumCamasDobles()).append(NUMERO_PERSONAS, habitacion.getNumeroMaximoPersonas());
        }
        if(habitacion instanceof Suite) {
            return new Document().append(PLANTA, habitacion.getPlanta()).append(PUERTA, habitacion.getPuerta()).append(IDENTIFICADOR, habitacion.getIdentificador()).append(PRECIO, habitacion.getPrecio()).append(TIPO, TIPO_SUITE).append(BANOS, ((Suite) habitacion).getNumBanos()).append(JACUZZI, ((Suite) habitacion).isTieneJacuzzi());
        }
        return null;
    }

    public static Habitacion getHabitacion(Document documentoHabitacion) {
        if(documentoHabitacion == null) {
            throw new NullPointerException("ERROR: El documento de una habitación no puede ser nulo.");
        }
        switch(documentoHabitacion.getString(TIPO)) {
            case TIPO_SIMPLE -> new Simple(Integer.parseInt(documentoHabitacion.getString(PLANTA)),Integer.parseInt(documentoHabitacion.getString(PUERTA)), Double.parseDouble(documentoHabitacion.getString(PRECIO)));
            case TIPO_DOBLE -> new Doble(Integer.parseInt(documentoHabitacion.getString(PLANTA)), Integer.parseInt(documentoHabitacion.getString(PUERTA)), Double.parseDouble(documentoHabitacion.getString(PRECIO)), Integer.parseInt(documentoHabitacion.getString(CAMAS_INDIVIDUALES)), Integer.parseInt(documentoHabitacion.getString(CAMAS_DOBLES)));
            case TIPO_TRIPLE -> new Triple(Integer.parseInt(documentoHabitacion.getString(PLANTA)), Integer.parseInt(documentoHabitacion.getString(PUERTA)), Double.parseDouble(documentoHabitacion.getString(PRECIO)), Integer.parseInt(documentoHabitacion.getString(BANOS)), Integer.parseInt(documentoHabitacion.getString(CAMAS_INDIVIDUALES)), Integer.parseInt(documentoHabitacion.getString(CAMAS_DOBLES)));
            case TIPO_SUITE -> new Suite(Integer.parseInt(documentoHabitacion.getString(PLANTA)), Integer.parseInt(documentoHabitacion.getString(PUERTA)), Double.parseDouble(documentoHabitacion.getString(PRECIO)), Integer.parseInt(documentoHabitacion.getString(BANOS)), Boolean.parseBoolean(documentoHabitacion.getString(JACUZZI)));
        }
        return null;
    }

    public Reserva getReserva(Document documentoReserva){
        if(documentoReserva == null) {
            throw new NullPointerException("ERROR: El documento de una reserva no pùede ser nulo.");
        }
        MongoCollection<Document> listaHuespedes = getBD().getCollection("reservas");
        Document BaseDatosDocumentHuesped = listaHuespedes.find().filter(eq(HUESPED_DNI,documentoReserva.getString(HUESPED_DNI))).first();
        MongoCollection<Document> listaHabitaciones = getBD().getCollection("reservas");
        Document BaseDatosDocumentHabitacion = listaHabitaciones.find().filter(eq(HABITACION_IDENTIFICADOR,documentoReserva.getString(HABITACION_IDENTIFICADOR))).first();
        Regimen regimen = null;
        Regimen [] tipoRegimen = Regimen.values();
        for(Regimen valor : tipoRegimen) {
            if(valor.toString().equals(documentoReserva.getString(REGIMEN))) {
                regimen = valor;
            }
        }
        String fechaInicioReservaCadena = documentoReserva.getString(FECHA_INICIO_RESERVA);
        LocalDate fechaInicioReserva = LocalDate.parse(fechaInicioReservaCadena,FORMATO_DIA);
        String fechaFinReservaCadena = documentoReserva.getString(FECHA_FIN_RESERVA);
        LocalDate fechaFinReserva = LocalDate.parse(fechaFinReservaCadena,FORMATO_DIA);
        Reserva reserva = new Reserva(getHuesped(BaseDatosDocumentHuesped),getHabitacion(BaseDatosDocumentHabitacion),regimen,fechaInicioReserva,fechaFinReserva,Integer.parseInt(documentoReserva.getString(NUMERO_PERSONAS)));

        if(documentoReserva.getString(CHECKIN) != null) {
            String CheckInCadena = documentoReserva.getString(CHECKIN);
            LocalDateTime checkIN = LocalDateTime.parse(CheckInCadena,FORMATO_DIA_HORA);
            reserva.setCheckIn(checkIN);
        }
        if(documentoReserva.getString(CHECKOUT) != null) {
            String CheckOutCadena = documentoReserva.getString(CHECKOUT);
            LocalDateTime checkOut = LocalDateTime.parse(CheckOutCadena,FORMATO_DIA_HORA);
            reserva.setCheckOut(checkOut);
        }
        return reserva;
    }
    public Document getDocumento(Reserva reserva){
        if(reserva == null) {
            throw new NullPointerException("ERROR: La reserva no puede ser nula.");
        }
        return new Document().append(HUESPED, reserva.getHuesped()).append(HABITACION, reserva.getHabitacion()).append(REGIMEN, reserva.getRegimen()).append(FECHA_INICIO_RESERVA, reserva.getFechaInicioReserva()).append(FECHA_FIN_RESERVA, reserva.getFechaFinReserva()).append(NUMERO_PERSONAS, reserva.getNumeroPersonas());
    }
}
