/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.json;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import model.entities.Passenger;
import model.repositories.PassengerRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author Scarta
 */
public class UploudJSONToPassengerRepository {
    private String path;
    private static final DateTimeFormatter DATE_FORMATTER = 
       DateTimeFormatter.ISO_LOCAL_DATE;
    private  PassengerRepository passengerRepo;

    public UploudJSONToPassengerRepository(String path) {
        this.path = path;
        this.passengerRepo = PassengerRepository.getInstance();
    }
    
    public void uploud(){
        try(FileReader fileReader = new FileReader(this.path)){
            JSONTokener tokener = new JSONTokener(fileReader);
            JSONArray jsonArray = new JSONArray(tokener);
            
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject json = jsonArray.getJSONObject(i);
                Passenger passenger = new Passenger(
                json.getLong("id"),
                json.getString("firstname"),
                json.getString("lastname"),
                LocalDate.parse(json.getString("birthDate"),DATE_FORMATTER),
                json.getInt("countryPhoneCode"),
                json.getLong("phone"),
                json.getString("country")
                );
                passengerRepo.add(passenger);
            }
            
        }catch(IOException e){
            System.out.println("Ha ocurrido el erro: "+ e);
        }
    }
}
