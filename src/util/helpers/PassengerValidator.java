/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.helpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import util.constants.ErrorMessages;
import util.enums.ResponseStatus;
import util.responses.Response;

/**
 *
 * @author dubalaguilar
 */
public class PassengerValidator {
   public static Response validateDomainRules(
        long id,
        String firstName,
        String lastName,
        String birthDateStr,
        int phoneCode,
        long phoneNumber,
        String country
    ) {
        // Validar nombre y apellido (no vacíos ya se validó en controller)
        if (firstName.length() < 2) {
            return Response.error(ResponseStatus.BAD_REQUEST, 
                "El nombre debe tener al menos 2 caracteres");
        }
        if (lastName.length() < 2) {
            return Response.error(ResponseStatus.BAD_REQUEST, 
                "El apellido debe tener al menos 2 caracteres");
        }

        // Validar código de teléfono (3 dígitos máximo)
        if (phoneCode < 0) {
            return Response.error(ResponseStatus.BAD_REQUEST, 
                "Código de teléfono no puede ser negativo");
        }
        if (String.valueOf(phoneCode).length() > 3) {
            return Response.error(ResponseStatus.BAD_REQUEST, 
                "Código de teléfono no puede tener más de 3 dígitos");
        }

        // Validar número de teléfono (11 dígitos máximo)
        if (phoneNumber < 0) {
            return Response.error(ResponseStatus.BAD_REQUEST, 
                "Número de teléfono no puede ser negativo");
        }
        if (String.valueOf(phoneNumber).length() > 11) {
            return Response.error(ResponseStatus.BAD_REQUEST, 
                "Número de teléfono no puede tener más de 11 dígitos");
        }

        // Validar fecha
        try {
            LocalDate birthDate = LocalDate.parse(birthDateStr);
            LocalDate now = LocalDate.now();
            
            if (birthDate.isAfter(now)) {
                return Response.error(ResponseStatus.BAD_REQUEST, 
                    "Fecha de nacimiento no puede ser futura");
            }
        } catch (DateTimeParseException e) {
            return Response.error(ResponseStatus.BAD_REQUEST, 
                "Fecha de nacimiento inexistente");
        }

        return Response.success();
    }
}
