package com.example.zerohunger.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.zerohunger.DTO.Coordinates;
import com.example.zerohunger.Entity.FoodBank;
import com.example.zerohunger.Repository.FoodBankRepo;

import jakarta.annotation.PostConstruct;

@Service
public class LocationService {
    private final FoodBankRepo foodBankRepo;
    private final RestTemplate restTemplate;

    public LocationService(FoodBankRepo foodBankRepo, RestTemplate restTemplate) {
        this.foodBankRepo = foodBankRepo;
        this.restTemplate = restTemplate;
    }
    
    // fills out the foodbanks table i made it fill up on launch
    public void populateFoodBanks() {
        System.out.println("🔄 Fetching food banks from multiple sources...");

        int totalAdded = 0;

        // Fetch from Overpass API
        try {
            String overpassUrl = "https://overpass-api.de/api/interpreter?data=[out:json];node[amenity=food_bank](51.2868,-0.5104,51.6919,0.3340);out;";
            ResponseEntity<Map> overpassResponse = restTemplate.getForEntity(overpassUrl, Map.class);
            int addedFromOverpass = parseOverpassResponse(overpassResponse.getBody());
            totalAdded += addedFromOverpass;
            System.out.println("✅ " + addedFromOverpass + " food banks added from Overpass API!");
        } catch (Exception e) {
            System.err.println("❌ Error fetching from Overpass API: " + e.getMessage());
        }

        // Fetch from Nominatim API with different search terms
        String[] queries = {"charity+London", "food+pantry+London", "soup+kitchen+London"};
        for (String query : queries) {
            try {
                String nominatimUrl = "https://nominatim.openstreetmap.org/search?format=json&q=" + query;
                ResponseEntity<Map[]> nominatimResponse = restTemplate.getForEntity(nominatimUrl, Map[].class);
                int addedFromNominatim = parseNominatimResponse(nominatimResponse.getBody());
                totalAdded += addedFromNominatim;
                System.out.println("✅ " + addedFromNominatim + " results processed from: " + nominatimUrl);
            } catch (Exception e) {
                System.err.println("❌ Error fetching from Nominatim API (" + query + "): " + e.getMessage());
            }
        }

        System.out.println("🎉 Total new food banks added: " + totalAdded);
    }




    
    @PostConstruct
    public void populateFoodBanksOnStartup() {
        populateFoodBanks();
    }
    
    //converts a string address into latitude and 
    //longitude and returns it in my custom Coordinates object
    public Coordinates getCoordinates(String address) {
        try {
            // 🔹 Encode the address for a URL
            String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);

            // 🔹 OpenStreetMap Nominatim API URL
            String url = "https://nominatim.openstreetmap.org/search?format=json&q=" + encodedAddress;

            // 🔹 Call API and get response as an array
            ResponseEntity<Map[]> response = restTemplate.getForEntity(url, Map[].class);

            // 🔹 Check if the response contains any data
            if (response.getBody() != null && response.getBody().length > 0) {
                Map<String, Object> locationData = response.getBody()[0];

                // 🔹 Extract latitude & longitude
                double lat = Double.parseDouble(locationData.get("lat").toString());
                double lon = Double.parseDouble(locationData.get("lon").toString());

                return new Coordinates(lat, lon);
            } else {
                throw new RuntimeException("No coordinates found for address: " + address);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error getting coordinates for address: " + address, e);
        }
    }
    
    //calculates km distance between two sets of coordinates
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Earth's radius in km

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // Distance in kilometers
    }
    
    //checks if entered address is valid
    public boolean isValidAddress(String address) {
        String url = "https://nominatim.openstreetmap.org/search?format=json&q=" + address.replace(" ", "+");
        
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getBody() != null && !response.getBody().equals("[]");
        } catch (Exception e) {
            System.err.println("Error validating address: " + e.getMessage());
            return false;
        }
    }
    
    //these next tow methods are for fillign the foodbank table but 
    //it needs to be done in two different ways so 2 methods for parsing
    private int parseOverpassResponse(Map<String, Object> response) {
        if (response == null || !response.containsKey("elements")) return 0;
        
        List<Map<String, Object>> elements = (List<Map<String, Object>>) response.get("elements");
        int count = 0;

        for (Map<String, Object> element : elements) {
            Map<String, Object> tags = (Map<String, Object>) element.get("tags");

            String name = tags != null ? (String) tags.getOrDefault("name", "Unknown Food Bank") : "Unknown Food Bank";
            double lat = (double) element.get("lat");
            double lon = (double) element.get("lon");
            String address = tags != null ? (String) tags.getOrDefault("addr:full", "No address available") : "No address available";

            if (!foodBankRepo.existsByName(name)) {
                FoodBank foodBank = new FoodBank();
                foodBank.setName(name);
                foodBank.setAddress(address);
                foodBank.setLat(lat);
                foodBank.setLong(lon);
                foodBankRepo.save(foodBank);
                count++;
            }
        }
        return count;
    }
    
    private int parseNominatimResponse(Map<String, Object>[] response) {
        if (response == null || response.length == 0) return 0;

        int count = 0;
        
        for (Map<String, Object> locationData : response) {
            String name = (String) locationData.getOrDefault("display_name", "Unknown Location");
            double lat = Double.parseDouble(locationData.get("lat").toString());
            double lon = Double.parseDouble(locationData.get("lon").toString());
            String address = name; // In Nominatim, display_name usually contains the full address.

            if (!foodBankRepo.existsByAddress(address)) {
                FoodBank foodBank = new FoodBank();
                foodBank.setName(name);
                foodBank.setAddress(address);
                foodBank.setLat(lat);
                foodBank.setLong(lon);
                foodBankRepo.save(foodBank);
                count++;
            }
        }
        return count;
    }




}