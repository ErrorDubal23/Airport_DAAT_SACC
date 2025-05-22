/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.repositories;

import java.util.List;
import model.entities.Plane;
import model.repositories.impl.BaseRepositoryImpl;

/**
 *
 * @author dubalaguilar
 */
public class PlaneRepository extends BaseRepositoryImpl<Plane, String> {
    private static PlaneRepository instance;

    private PlaneRepository() {}

    public static PlaneRepository getInstance() {
        if (instance == null) {
            instance = new PlaneRepository();
        }
        return instance;
    }

    @Override
    protected String getId(Plane plane) {
        return plane.getId();
    }

    // Métodos específicos para Plane
    public List<Plane> findByAirline(String airline) {
        return items.stream()
                .filter(plane -> plane.getAirline().equalsIgnoreCase(airline))
                .toList();
    }

    public List<Plane> findByBrand(String brand) {
        return items.stream()
                .filter(plane -> plane.getBrand().equalsIgnoreCase(brand))
                .toList();
    }
}
