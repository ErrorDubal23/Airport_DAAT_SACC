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
    private static final DecimalFormat COORDINATE_FORMAT = new DecimalFormat("#.####");
    private static final int MAX_DECIMAL_DIGITS = 4;

    public static Response validate(
        String airportId,
        String name,
        String city,
        String country,
        String latitudeStr,
        String longitudeStr
    ) {
        // Validacion 1: Campos no vacíos
        if (airportId.isEmpty() || name.isEmpty() || city.isEmpty() || country.isEmpty()) {
            return Response.error(ResponseStatus.BAD_REQUEST, 
                String.format(ErrorMessages.FIELD_REQUIRED, "Todos los campos"));
        }

        // Validacion 2: Formato del ID
        if (!airportId.matches("^[A-Z]{3}$")) {
            return Response.error(ResponseStatus.BAD_REQUEST, ErrorMessages.LOCATION_ID_FORMAT);
        }

        // Validacion 3: Coordenadas numericas
        try {
            double latitude = Double.parseDouble(latitudeStr);
            double longitude = Double.parseDouble(longitudeStr);

            // Validacion 4: Rango de coordenadas
            if (latitude < -90 || latitude > 90) {
                return Response.error(ResponseStatus.BAD_REQUEST, ErrorMessages.INVALID_LATITUDE);
            }
            if (longitude < -180 || longitude > 180) {
                return Response.error(ResponseStatus.BAD_REQUEST, ErrorMessages.INVALID_LONGITUDE);
            }

            // Validacion 5: Máximo 4 decimales (maneja casos sin punto decimal)
            if (latitudeStr.contains(".")) {
                String decimalPart = latitudeStr.split("\\.")[1];
                if (decimalPart.length() > MAX_DECIMAL_DIGITS) {
                    return Response.error(ResponseStatus.BAD_REQUEST, 
                        "Latitud: máximo " + MAX_DECIMAL_DIGITS + " decimales permitidos");
                }
            }
            
            if (longitudeStr.contains(".")) {
                String decimalPart = longitudeStr.split("\\.")[1];
                if (decimalPart.length() > MAX_DECIMAL_DIGITS) {
                    return Response.error(ResponseStatus.BAD_REQUEST, 
                        "Longitud: máximo " + MAX_DECIMAL_DIGITS + " decimales permitidos");
                }
            }

        } catch (NumberFormatException e) {
            return Response.error(ResponseStatus.BAD_REQUEST, ErrorMessages.COORDINATES_INVALID);
        }

        return Response.success();
    }
}
