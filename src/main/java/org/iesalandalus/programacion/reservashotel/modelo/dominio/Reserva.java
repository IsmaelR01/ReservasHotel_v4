package org.iesalandalus.programacion.reservashotel.modelo.dominio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Reserva {
    public static final int MAX_NUMERO_MESES_RESERVA = 6;
    private static final int MAX_HORAS_POSTERIOR_CHECKOUT = 12;
    public static final String FORMATO_FECHA_RESERVA = "dd/MM/yyyy";
    public static final String FORMATO_FECHA_HORA_RESERVA = "dd/MM/yyyy HH:mm:ss";
    private Huesped huesped;
    private Habitacion habitacion;
    private Regimen regimen;
    private LocalDate fechaInicioReserva;
    private LocalDate fechaFinReserva;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private double precio;
    private int numeroPersonas;

    public Reserva(Huesped huesped, Habitacion habitacion, Regimen regimen, LocalDate fechaInicioReserva, LocalDate fechaFinReserva, int numeroPersonas) {
        setHuesped(huesped);
        setHabitacion(habitacion);
        setRegimen(regimen);
        setFechaInicioReserva(fechaInicioReserva);
        setFechaFinReserva(fechaFinReserva);
        setNumeroPersonas(numeroPersonas);
        precio = 0;
    }

    public Reserva(Reserva reserva) {
        if (reserva == null) {
            throw new NullPointerException("ERROR: No es posible copiar una reserva nula.");
        }

        setHuesped(reserva.getHuesped());
        setHabitacion(reserva.getHabitacion());
        setRegimen(reserva.getRegimen());
        setFechaInicioReserva(reserva.getFechaInicioReserva());
        setFechaFinReserva(reserva.getFechaFinReserva());
        setNumeroPersonas(reserva.getNumeroPersonas());
        if(reserva.getCheckIn() != null) {
            setCheckIn(reserva.getCheckIn());
        }
        if(reserva.getCheckOut() != null) {
            setCheckOut(reserva.getCheckOut());
            precio = reserva.getPrecio();
        }
    }

    public Huesped getHuesped() {
        return new Huesped(huesped);
    }

    public void setHuesped(Huesped huesped) {
        if (huesped == null) {
            throw new NullPointerException("ERROR: El huésped de una reserva no puede ser nulo.");
        }
        this.huesped = new Huesped(huesped);
    }

    public Habitacion getHabitacion() {
        if(habitacion instanceof Simple) {
            return new Simple((Simple) habitacion);
        }
        if(habitacion instanceof Doble) {
            return new Doble((Doble) habitacion);
        }
        if(habitacion instanceof Triple) {
            return new Triple((Triple) habitacion);
        }
        if(habitacion instanceof Suite) {
            return new Suite((Suite) habitacion);
        }
        return null;
    }

    public void setHabitacion(Habitacion habitacion) {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: La habitación de una reserva no puede ser nula.");
        }
        if(habitacion instanceof Simple) {
            this.habitacion = new Simple((Simple) habitacion);
        }
        if(habitacion instanceof Doble) {
            this.habitacion = new Doble((Doble) habitacion);
        }
        if(habitacion instanceof Triple) {
            this.habitacion = new Triple((Triple) habitacion);
        }
        if(habitacion instanceof Suite) {
            this.habitacion = new Suite((Suite) habitacion);
        }

    }

    public Regimen getRegimen() {
        return regimen;
    }

    public void setRegimen(Regimen regimen) {
        if (regimen == null) {
            throw new NullPointerException("ERROR: El régimen de una reserva no puede ser nulo.");
        }
        this.regimen = regimen;
    }

    public LocalDate getFechaInicioReserva() {
        return fechaInicioReserva;
    }

    public void setFechaInicioReserva(LocalDate fechaInicioReserva) {
        if (fechaInicioReserva == null) {
            throw new NullPointerException("ERROR: La fecha de inicio de una reserva no puede ser nula.");
        }
        if(fechaInicioReserva.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("ERROR: La fecha de inicio de la reserva no puede ser anterior al día de hoy.");
        }


        if(fechaInicioReserva.isAfter(LocalDate.now().plusMonths(MAX_NUMERO_MESES_RESERVA))) {
            throw new IllegalArgumentException("ERROR: La fecha de inicio de la reserva no puede ser posterior a seis meses.");
        }


        this.fechaInicioReserva = fechaInicioReserva;
    }

    public LocalDate getFechaFinReserva() {
        return fechaFinReserva;
    }

    public void setFechaFinReserva(LocalDate fechaFinReserva) {
        if (fechaFinReserva == null) {
            throw new NullPointerException("ERROR: La fecha de fin de una reserva no puede ser nula.");
        }
        if(!fechaFinReserva.isAfter(fechaInicioReserva)) {
            throw new IllegalArgumentException("ERROR: La fecha de fin de la reserva debe ser posterior a la de inicio.");
        }
        if(fechaFinReserva.isEqual(fechaInicioReserva)) {
            throw new IllegalArgumentException("ERROR: La fecha de fin de la reserva no puede ser igual a la fecha de inicio de la reserva.");
        }
        this.fechaFinReserva = fechaFinReserva;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        if(checkIn == null) {
            throw new NullPointerException("ERROR: El checkin de una reserva no puede ser nulo.");
        }
        if(checkIn.isBefore(fechaInicioReserva.atStartOfDay())) {
            throw new IllegalArgumentException("ERROR: El checkin de una reserva no puede ser anterior a la fecha de inicio de la reserva.");
        }
        this.checkIn = checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        if(checkOut == null) {
            throw new NullPointerException("ERROR: El checkout de una reserva no puede ser nulo.");
        }
        if(checkOut.isBefore(checkIn)) {
            throw new IllegalArgumentException("ERROR: El checkout de una reserva no puede ser anterior al checkin.");
        }
        if (checkOut.isAfter(fechaFinReserva.atStartOfDay().plusHours(MAX_HORAS_POSTERIOR_CHECKOUT))) {
            throw  new IllegalArgumentException("ERROR: El checkout de una reserva puede ser como máximo 12 horas después de la fecha de fin de la reserva.");
        }
        this.checkOut = checkOut;
        setPrecio();
    }

    public double getPrecio() {
        return precio;
    }

    private void setPrecio() {
       int numdias = (int) (fechaFinReserva.toEpochDay() - fechaInicioReserva.toEpochDay());
        this.precio = (habitacion.getPrecio() + regimen.getIncrementoPrecio()) * (numeroPersonas * numdias);
    }

    public int getNumeroPersonas() {
        return numeroPersonas;
    }

    public void setNumeroPersonas(int numeroPersonas) {
        if(numeroPersonas <= 0) {
            throw new IllegalArgumentException("ERROR: El número de personas de una reserva no puede ser menor o igual a 0.");
        }
        if(numeroPersonas > this.habitacion.getNumeroMaximoPersonas()) {
            throw new IllegalArgumentException("ERROR: El número de personas de una reserva no puede superar al máximo de personas establacidas para el tipo de habitación reservada.");
        }
        /*
        if(numeroPersonas > TipoHabitacion.SUITE.getNumeroMaximoPersonas()) {
            throw new IllegalArgumentException("ERROR: No se puede superar el m?ximo de personas permitido en la habitaci?n suite.");
        }
        if (numeroPersonas > TipoHabitacion.DOBLE.getNumeroMaximoPersonas()) {
            throw new IllegalArgumentException("ERROR: No se puede superar el m?ximo de personas permitido en la habitaci?n doble.");
        }
        if(numeroPersonas > TipoHabitacion.TRIPLE.getNumeroMaximoPersonas()) {
            throw new IllegalArgumentException("ERROR: No se puede superar el m?ximo de personas permitido en la habitaci?n triple.");
        }
        if(numeroPersonas > TipoHabitacion.SIMPLE.getNumeroMaximoPersonas()) {
            throw new IllegalArgumentException("ERROR: No se puede superar el m?ximo de personas permitido en la habitaci?n simpple.");
        }

         */
        this.numeroPersonas = numeroPersonas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(habitacion, reserva.habitacion) && Objects.equals(fechaInicioReserva, reserva.fechaInicioReserva);
    }

    @Override
    public int hashCode() {
        return Objects.hash(habitacion, fechaInicioReserva);
    }

    @Override
    public String toString() {
        String cadCheckin = "No registrado";
        if(getCheckIn()!= null) {
            cadCheckin = getCheckIn().format(DateTimeFormatter.ofPattern(FORMATO_FECHA_HORA_RESERVA));
        }
        String cadCheckout = "No registrado";
        if(getCheckOut()!= null) {
            cadCheckout = getCheckOut().format(DateTimeFormatter.ofPattern(FORMATO_FECHA_HORA_RESERVA));
        }
        return String.format("Huesped: %s %s Habitación:%s Fecha Inicio Reserva: %s Fecha Fin Reserva: %s Checkin: %s Checkout: %s Precio: %.2f Personas: %s",huesped.getNombre(),huesped.getDni(),habitacion.toString(),fechaInicioReserva.format(DateTimeFormatter.ofPattern(FORMATO_FECHA_RESERVA)),fechaFinReserva.format(DateTimeFormatter.ofPattern(FORMATO_FECHA_RESERVA)),cadCheckin,cadCheckout,precio,numeroPersonas);
    }



}
