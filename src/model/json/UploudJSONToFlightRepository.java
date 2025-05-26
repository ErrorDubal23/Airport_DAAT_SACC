/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.json;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import model.entities.Flight;
import model.repositories.FlightRepository;
import model.repositories.LocationRepository;
import model.repositories.PlaneRepository;
import model.repositories.impl.BaseRepositoryImpl;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONArray;
/**
 *
 * @author Scarta
 */
public class UploudJSONToFlightRepository {
    //private BaseRepositoryImpl<Flight, String> repository;
    private String path;
    private final FlightRepository flightRepo;
    private final LocationRepository locationRepo;
    private final PlaneRepository planeRepo;
    private static final DateTimeFormatter DATE_FORMATTER = 
        DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    public UploudJSONToFlightRepository(String path) {
        this.planeRepo = PlaneRepository.getInstance();
        this.flightRepo = FlightRepository.getInstance();
        this.locationRepo = LocationRepository.getInstance();
        this.path = path;
        
    }
    
    public void uploud(){
        try(FileReader fileReader = new FileReader(this.path)){
            JSONTokener tokener = new JSONTokener(fileReader);
            JSONArray jsonArray = new JSONArray(tokener);
            
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject json = jsonArray.getJSONObject(i);
                Flight flight = new Flight(
                        json.getString("id"),
                        planeRepo.getByID(json.getString("plane")),
                        locationRepo.getByID(json.getString("departureLocation")),
                        locationRepo.getByID(json.optString("scaleLocation")),
                        locationRepo.getByID(json.getString("arrivalLocation")),
                        LocalDateTime.parse(json.getString("departureDate"),DATE_FORMATTER),
                        json.getInt("hoursDurationArrival"),
                        json.getInt("minutesDurationArrival"),
                        json.getInt("hoursDurationScale"),
                        json.getInt("minutesDurationScale")
                );
                flightRepo.add(flight);
            }
            
        }catch(IOException e){
            System.out.println("Ha ocurrido el erro: "+ e);
        }
    }
    
}
