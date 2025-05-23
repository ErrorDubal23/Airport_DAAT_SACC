/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.services;

import java.util.Comparator;
import java.util.List;
import model.entities.Plane;
import model.repositories.PlaneRepository;
import model.repositories.impl.Repository;
import util.enums.ResponseStatus;
import util.helpers.PlaneValidator;
import util.responses.Response;

/**
 *
 * @author dubalaguilar
 */
public class PlaneService {
    private final Repository<Plane, String> planeRepository;
    
    private static PlaneService instance;

    private PlaneService(Repository<Plane, String> planeRepository) {
        this.planeRepository = planeRepository;
    }

    public static PlaneService getInstance() {
        if (instance == null) {
            instance = new PlaneService(PlaneRepository.getInstance());
        }
        return instance;
    }

    public Response registerPlane(
        String planeId,
        String brand,
        String model,
        String maxCapacityStr,
        String airline
    ) {
        // Validación
        Response validation = PlaneValidator.validate(
            planeId, brand, model, maxCapacityStr, airline
        );
        if (!validation.isSuccess()) {
            return validation;
        }

        // Verificar si el avión ya existe
        if (planeRepository.findById(planeId).isPresent()) {
            return Response.error(ResponseStatus.CONFLICT, "Ya existe un avión con este ID");
        }

        // Crear y guardar el avión
        try {
            int maxCapacity = Integer.parseInt(maxCapacityStr);
            Plane plane = new Plane(planeId, brand, model, maxCapacity, airline);
            planeRepository.add(plane);
            return Response.success(ResponseStatus.CREATED, "Avión registrado exitosamente", plane);
        } catch (NumberFormatException e) {
            return Response.error(ResponseStatus.BAD_REQUEST, "La capacidad máxima debe ser un número válido");
        } catch (Exception e) {
            return Response.error(ResponseStatus.INTERNAL_ERROR, "Error al registrar avión: " + e.getMessage());
        }
    }

    public List<Plane> getAllPlanesSorted() {
        List<Plane> planes = planeRepository.findAll();
        planes.sort(Comparator.comparing(Plane::getId));
        return planes;
    }

    public Response getPlaneById(String planeId) {
        return planeRepository.findById(planeId)
            .map(plane -> Response.success(plane))
            .orElse(Response.error(ResponseStatus.NOT_FOUND, "Avión no encontrado"));
    }

    public int getNumberOfFlightsForPlane(String planeId) {
        return planeRepository.findById(planeId)
            .map(plane -> plane.getFlights().size())
            .orElse(0);
    }
}
