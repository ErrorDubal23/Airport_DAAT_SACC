/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.constants;

/**
 *
 * @author dubalaguilar
 */

// En esta clase estaran los error especificos, se iremos modificando a medida que validando
public class ErrorMessages {
    
    //Errores del Passenger
    public static final String PASSENGER_ID_INVALID = "ID inválido: debe ser positivo y tener máximo 15 dígitos";
    public static final String PASSENGER_DUPLICATE = "Ya existe un pasajero con este ID";
    public static final String PASSENGER_NOT_FOUND = "Pasejero no ha sido encontrado"; //Dudo que utilizaremos esta, pero la dejare por le momento
    
    //Errores para los Flight
    public static final String FLIGHT_ID_FORMAT = "Formato del ID es inválido: debe de ser XXXYY (3 letras más 3 numeros)";
    public static final String FLIGHT_DATE_PAST = "La fecha de vuelo no puede ser en el pasado";
    public static final String FLIGHT_DURATION_INVALID = "La duración del vuelo debe ser mayor a 00:00";
    public static final String SCALE_TIME_INVALID = "El tiempo de escala debe ser mayor a 00:00 si existe escala";
    public static final String RELATED_RESOURCE_NOT_FOUND = "Avión o ubicación no encontrados";
    public static final String FLIGHT_NOT_FOUND = "Vuelo no encontrado";
    public static final String DELAY_TIME_INVALID = "El tiempo de retraso debe ser positivo";
    public static final String FLIGHT_FULL_CAPACITY = "El vuelo ha alcanzado su capacidad máxima";
    public static final String PASSENGER_ALREADY_IN_FLIGHT = "El pasajero ya está registrado en este vuelo";

    
    // Para Location
    public static final String LOCATION_ID_FORMAT = "El ID del aeropuerto debe tener exactamente 3 letras mayúsculas (ej: BOG)";
    public static final String LOCATION_DUPLICATE = "Ya existe un aeropuerto con este ID";
    public static final String COORDINATES_INVALID = "Latitud y longitud deben ser números válidos (ej: 4.7123)";
    public static final String INVALID_LATITUDE = "La latitud debe estar entre -90 y 90 grados";
    public static final String INVALID_LONGITUDE = "La longitud debe estar entre -180 y 180 grados";
    public static final String LOCATION_ALREADY_EXISTS = "Ya existe un aeropuerto con este ID";
    public static final String LOCATION_NOT_FOUND = "Aeropuerto no encontrado";
    
    //Errores comunes
    public static final String FIELD_REQUIRED = "Todos los campos son obligatorios"; 
    public static final String INVALID_DATE_RANGE = "Rango de fechas inválido";
    
    //Esto es para mensajes dinamicos con ayuda de helpers (No es seguro que se termine de implementar)
    public static String formatFieldRequired(String fieldName) {
        return String.format(FIELD_REQUIRED, fieldName);
    }

   
    private  ErrorMessages(){ } // Se pone el constructor privado para evitar instancias 
    
}
