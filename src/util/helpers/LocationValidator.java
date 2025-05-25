/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.helpers;

import java.text.DecimalFormat;
import util.constants.ErrorMessages;
import util.enums.ResponseStatus;
import util.responses.Response;

/**
 *
 * @author dubalaguilar
 */
public final class LocationValidator {
   public static Response validateDomainRules(
        String airportId,
        String name,
        String city,
        String country,
        double latitude,
        double longitude
    ) {
        // Validación de longitud mínima para nombres
        if (name.length() < 3) {
            return Response.error(ResponseStatus.BAD_REQUEST, 
                "El nombre del aeropuerto debe tener al menos 3 caracteres");
        }

        if (city.length() < 2) {
            return Response.error(ResponseStatus.BAD_REQUEST, 
                "La ciudad debe tener al menos 2 caracteres");
        }

        if (country.length() < 2) {
            return Response.error(ResponseStatus.BAD_REQUEST, 
                "El país debe tener al menos 2 caracteres");
        }

        // Validación adicional de caracteres no permitidos en nombres
        if (!name.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            return Response.error(ResponseStatus.BAD_REQUEST,
                "El nombre del aeropuerto solo puede contener letras y espacios");
        }

        return Response.success();
    }
}
