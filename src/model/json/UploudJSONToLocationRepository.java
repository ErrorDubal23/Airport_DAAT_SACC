/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.json;

import java.io.FileReader;
import java.io.IOException;
import model.entities.Location;
import model.repositories.LocationRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author Scarta
 */
public class UploudJSONToLocationRepository {
    String path;
    private final LocationRepository locationRepo;

    public UploudJSONToLocationRepository(String path) {
        this.locationRepo = LocationRepository.getInstance();
        this.path = path;
    }
    
    public void uploud(){
        try(FileReader fileReader = new FileReader(this.path)){
            JSONTokener tokener = new JSONTokener(fileReader);
            JSONArray jsonArray = new JSONArray(tokener);
            
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject json = jsonArray.getJSONObject(i);
                Location location = new Location(
                json.getString("airportId"),
                json.getString("airportName"),
                json.getString("airportCity"),
                json.getString("airportCountry"),
                json.getDouble("airportLatitude"),
                json.getDouble("airportLongitude")
                );
                locationRepo.add(location);
            }
            
        }catch(IOException e){
            System.out.println("Ha ocurrido el erro: "+ e);
        }
    }
    
}
