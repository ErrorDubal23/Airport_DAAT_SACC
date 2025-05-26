/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.services;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import model.entities.Flight;
import model.entities.Passenger;
import model.repositories.PassengerRepository;
import model.repositories.impl.Repository;
import util.constants.ErrorMessages;
import util.enums.ResponseStatus;
import util.helpers.PassengerValidator;
import util.responses.Response;

/**
 *
 * @author dubalaguilar
 */
public class PassengerService {

    private final Repository<Passenger, Long> passengerRepository;

    private static PassengerService instance;

    private PassengerService(Repository<Passenger, Long> passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public static PassengerService getInstance() {
        if (instance == null) {
            instance = new PassengerService(PassengerRepository.getInstance());
        }
        return instance;
    }

    public Response registerPassenger(
            long id,
            String firstName,
            String lastName,
            String birthDateStr,
            int phoneCode,
            long phoneNumber,
            String country
    ) {
        try {
            // Validar ID (15 dígitos máximo)
            if (id < 0) {
                return Response.error(ResponseStatus.BAD_REQUEST, "ID no puede ser negativo");
            }
            if (String.valueOf(id).length() > 15) {
                return Response.error(ResponseStatus.BAD_REQUEST, "ID no puede tener más de 15 dígitos");
            }

            // Validaciones específicas con PassengerValidator
            Response validation = PassengerValidator.validateDomainRules(
                    id, firstName, lastName, birthDateStr, phoneCode, phoneNumber, country
            );
            if (!validation.isSuccess()) {
                return validation;
            }

            // Verificar duplicados
            if (passengerRepository.findById(id).isPresent()) {
                return Response.error(ResponseStatus.CONFLICT, ErrorMessages.PASSENGER_DUPLICATE);
            }

            // Crear y guardar pasajero
            LocalDate birthDate = LocalDate.parse(birthDateStr);
            Passenger passenger = new Passenger(id, firstName, lastName, birthDate, phoneCode, phoneNumber, country);
            passengerRepository.add(passenger);

            return Response.success(ResponseStatus.CREATED, "Pasajero registrado exitosamente", passenger);

        } catch (DateTimeParseException e) {
            return Response.error(ResponseStatus.BAD_REQUEST, "Fecha de nacimiento inválida");
        } catch (Exception e) {
            return Response.error(ResponseStatus.INTERNAL_ERROR,
                    "Error al registrar pasajero: " + e.getMessage());
        }
    }

    public Response updatePassenger(
            long id,
            String firstName,
            String lastName,
            String birthDateStr,
            int phoneCode,
            long phoneNumber,
            String country
    ) {
        try {
            // Validaciones específicas con PassengerValidator
            Response validation = PassengerValidator.validateDomainRules(
                    id, firstName, lastName, birthDateStr, phoneCode, phoneNumber, country
            );
            if (!validation.isSuccess()) {
                return validation;
            }

            // Verificar existencia del pasajero
            Optional<Passenger> existingPassenger = passengerRepository.findById(id);
            if (existingPassenger.isEmpty()) {
                return Response.error(ResponseStatus.NOT_FOUND, ErrorMessages.PASSENGER_NOT_FOUND);
            }

            // Validar fecha de nacimiento
            LocalDate birthDate = LocalDate.parse(birthDateStr);
            LocalDate now = LocalDate.now();
            if (birthDate.isAfter(now.minusYears(1))) {
                return Response.error(ResponseStatus.BAD_REQUEST, "El pasajero debe tener al menos 1 año");
            }
            if (birthDate.isBefore(now.minusYears(120))) {
                return Response.error(ResponseStatus.BAD_REQUEST, "Edad inválida (máximo 120 años)");
            }

            // Validar nombre y apellido
            if (firstName.length() < 2 || lastName.length() < 2) {
                return Response.error(ResponseStatus.BAD_REQUEST,
                        "Nombre y apellido deben tener al menos 2 caracteres");
            }

            // Validar país
            if (country.length() < 2) {
                return Response.error(ResponseStatus.BAD_REQUEST,
                        "El país debe tener al menos 2 caracteres");
            }

            // Actualizar pasajero
            Passenger updatedPassenger = new Passenger(id, firstName, lastName, birthDate, phoneCode, phoneNumber, country);
            updatedPassenger.setFlights(existingPassenger.get().getFlights()); // Mantener vuelos existentes

            passengerRepository.update(updatedPassenger);
            return Response.success("Información del pasajero actualizada exitosamente", updatedPassenger);

        } catch (DateTimeParseException e) {
            return Response.error(ResponseStatus.BAD_REQUEST, "Fecha de nacimiento inválida");
        } catch (Exception e) {
            return Response.error(ResponseStatus.INTERNAL_ERROR,
                    "Error al actualizar pasajero: " + e.getMessage());
        }
    }

    public List<Passenger> getAllPassengersSorted() {
        List<Passenger> passengers = passengerRepository.findAll();
        passengers.sort(Comparator.comparingLong(Passenger::getId));
        return passengers;
    }

    public Response getPassengerById(long id) {
        return passengerRepository.findById(id)
                .map(passenger -> Response.success(passenger))
                .orElse(Response.error(ResponseStatus.NOT_FOUND, ErrorMessages.PASSENGER_NOT_FOUND));
    }

    public List<Flight> getPassengerFlightsSorted(long passengerId) {
        return passengerRepository.findById(passengerId)
                .map(passenger -> {
                    List<Flight> flights = new ArrayList<>(passenger.getFlights());
                    flights.sort(Comparator.comparing(Flight::getDepartureDate));
                    return flights;
                })
                .orElse(Collections.emptyList());
    }
}
