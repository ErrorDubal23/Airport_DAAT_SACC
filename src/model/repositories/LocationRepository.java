/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.repositories;

import java.util.List;
import model.entities.Location;
import model.repositories.impl.BaseRepositoryImpl;

/**
 *
 * @author dubalaguilar
 */
public class LocationRepository extends BaseRepositoryImpl<Location, String> {
    private static LocationRepository instance;

    private LocationRepository() {}

    public static LocationRepository getInstance() {
        if (instance == null) {
            instance = new LocationRepository();
        }
        return instance;
    }

    @Override
    protected String getId(Location location) {
        return location.getAirportId();
    }

   
    public List<Location> findByCountry(String country) {
        return items.stream()
                .filter(location -> location.getAirportCountry().equalsIgnoreCase(country))
                .toList();
    }
}