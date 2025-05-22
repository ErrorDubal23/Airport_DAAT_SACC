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
    
    //Errores comunes
    public static final String FIELD_REQUIRED = "El campo '%s' es obligatorio";
    public static final String INVALID_DATE_RANGE = "Rango de fechas inválido";
    
    //Esto es para mensajes dinamicos con ayuda de helpers (No es seguro que se termine de implementar)
    public static String formatFieldRequired(String fieldName) {
        return String.format(FIELD_REQUIRED, fieldName);
    }

   
    private  ErrorMessages(){ } // Se pone el constructor privado para evitar instancias 
    
}
