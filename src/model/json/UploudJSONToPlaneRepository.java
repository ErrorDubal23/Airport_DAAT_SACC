/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.json;

import java.io.FileReader;
import java.io.IOException;
import model.entities.Plane;
import model.repositories.PlaneRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author Usuario
 */
public class UploudJSONToPlaneRepository {
    private String path;
    private final PlaneRepository planeRepo;

    public UploudJSONToPlaneRepository(String path) {
        this.path = path;
        this.planeRepo = PlaneRepository.getInstance();
    }
    
    public void uploud(){
        try(FileReader fileReader = new FileReader(this.path)){
            JSONTokener tokener = new JSONTokener(fileReader);
            JSONArray jsonArray = new JSONArray(tokener);
            
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject json = jsonArray.getJSONObject(i);
                Plane plane = new Plane(
                json.getString("id"),
                json.getString("brand"),
                json.getString("model"),
                json.getInt("maxCapacity"),
                json.getString("airline")
                );
                planeRepo.add(plane);
            }
            
        }catch(IOException e){
            System.out.println("Ha ocurrido el erro: "+ e);
        }
    }
    
}
