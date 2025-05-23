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
   private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final int MAX_ID_DIGITS = 15;
    private static final int MAX_PHONE_CODE_DIGITS = 3;
    private static final int MAX_PHONE_DIGITS = 11;

    public static Response validate(
        long id,
        String firstName,
        String lastName,
        String birthDateStr,
        int phoneCode,
        long phoneNumber,
        String country
    ) {
        // Validación 1: ID único, mayor o igual a 0 y máximo 15 dígitos
        if (id < 0) {
            return Response.error(ResponseStatus.BAD_REQUEST, ErrorMessages.PASSENGER_ID_INVALID);
        }
        if (String.valueOf(id).length() > MAX_ID_DIGITS) {
            return Response.error(ResponseStatus.BAD_REQUEST, ErrorMessages.PASSENGER_ID_INVALID);
        }

        // Validación 2: Campos no vacíos
        if (firstName == null || firstName.trim().isEmpty() ||
            lastName == null || lastName.trim().isEmpty() ||
            country == null || country.trim().isEmpty()) {
            return Response.error(ResponseStatus.BAD_REQUEST, ErrorMessages.FIELD_REQUIRED);
        }

        // Validación 3: Fecha de nacimiento válida
        try {
            LocalDate birthDate = LocalDate.parse(birthDateStr, DATE_FORMATTER);
            if (birthDate.isAfter(LocalDate.now())) {
                return Response.error(ResponseStatus.BAD_REQUEST, "La fecha de nacimiento no puede ser futura");
            }
        } catch (DateTimeParseException e) {
            return Response.error(ResponseStatus.BAD_REQUEST, "Formato de fecha inválido. Use YYYY-MM-DD");
        }

        // Validación 4: Código telefónico (máximo 3 dígitos, positivo)
        if (phoneCode < 0) {
            return Response.error(ResponseStatus.BAD_REQUEST, "El código telefónico debe ser positivo");
        }
        if (String.valueOf(phoneCode).length() > MAX_PHONE_CODE_DIGITS) {
            return Response.error(ResponseStatus.BAD_REQUEST, "El código telefónico debe tener máximo " + MAX_PHONE_CODE_DIGITS + " dígitos");
        }

        // Validación 5: Número telefónico (máximo 11 dígitos, positivo)
        if (phoneNumber < 0) {
            return Response.error(ResponseStatus.BAD_REQUEST, "El número telefónico debe ser positivo");
        }
        if (String.valueOf(phoneNumber).length() > MAX_PHONE_DIGITS) {
            return Response.error(ResponseStatus.BAD_REQUEST, "El número telefónico debe tener máximo " + MAX_PHONE_DIGITS + " dígitos");
        }

        return Response.success();
    } 
}
