/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.repositories;

import java.util.List;
import model.entities.Passenger;
import model.repositories.impl.BaseRepositoryImpl;

/**
 *
 * @author dubalaguilar
 */
public class PassengerRepository extends BaseRepositoryImpl<Passenger, Long> {
    private static PassengerRepository instance;

    private PassengerRepository() {}

    public static PassengerRepository getInstance() {
        if (instance == null) {
            instance = new PassengerRepository();
        }
        return instance;
    }

    @Override
    protected Long getId(Passenger passenger) {
        return passenger.getId();
    }

    // Métodos específicos para Passenger
    public List<Passenger> findByCountry(String country) {
        return items.stream()
                .filter(passenger -> passenger.getCountry().equalsIgnoreCase(country))
                .toList();
    }

    public List<Passenger> findByName(String name) {
        return items.stream()
                .filter(passenger -> passenger.getFullname().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }
}
