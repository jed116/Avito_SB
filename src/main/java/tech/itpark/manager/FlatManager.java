package tech.itpark.manager;

import tech.itpark.model.Flat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlatManager {
    private List<Flat> items = new ArrayList<Flat>();
    private int nextId = 1;
    private double deltaPrice;
    private int deltaFloor;

    public FlatManager(double deltaPrice, int deltaFloor) {
        this.deltaPrice = deltaPrice;
        this.deltaFloor = deltaFloor;
    }

    public List<Flat> getAll(){
        return items;
    }

    public Flat getFlatById(int id){
        for (Flat item : items) {
            if (item.getId() == id){
                return item;
            }
        }
        return null;
    }

    public Flat save(Flat item) {
        if (item.getId() == 0) {
            item.setId(nextId++);
            items.add(item);
            return item;
        }
        Flat saved = getFlatById(item.getId());
        if(saved != null){
            saved.setRooms(item.getRooms());
            saved.setFloor(item.getFloor());
            saved.setPrice(item.getPrice());
            saved.setDistrict(item.getDistrict());
            saved.setStations(item.getStations());
        }

        return saved;
    }

    public Flat removeById(int id){
        Flat deleted = getFlatById(id);
        if (items.removeIf(ob -> ob.getId() == id)){
            return deleted;
        }
        return null;
    }

    public ArrayList<Flat> search(String text, boolean serchByDistrict, boolean serchByStation){
        ArrayList<Flat> result = new ArrayList<>();
        for (Flat item : items) {
            if (((containsAnyDistrict(item, new ArrayList<String>(Arrays.asList(text)))) && (serchByDistrict)) ||
                ((containsAnyStation(item,  new ArrayList<String>(Arrays.asList(text)))) && (serchByStation ))){
                result.add(item);
            }
        }
        return result;
    }

    public ArrayList<Flat> filter(int rooms, double minPrice, double maxPrice, int minFloor, int maxFloor, ArrayList<String> districts, ArrayList<String> stations) {
        ArrayList<Flat> result = new ArrayList<>();

        for (Flat item : items) {
            if ((item.getRooms() != rooms) && (rooms > 0)){
                continue;
            }

            if ((item.getPrice() < minPrice - deltaPrice) && (minPrice > 0.0)){
                continue;
            }
            if ((item.getPrice() > maxPrice + deltaPrice) && (maxPrice > 0.0)){
                continue;
            }

            if ((item.getFloor() < minFloor - deltaFloor) && (minFloor > 0)){
                continue;
            }
            if ((item.getFloor() > maxFloor + deltaFloor) && (maxFloor > 0)){
                continue;
            }

            if ((!containsAnyDistrict(item, districts)) && (!districts.isEmpty())) {
                continue;
            }

            if ((!containsAnyStation(item, stations)) && (!stations.isEmpty())){
                continue;
            }
            result.add(item);
        }
        return result;
    }

    private boolean containsAnyDistrict(Flat item, ArrayList<String> districts) {
        for (String district : districts) {
            if (item.getDistrict().toLowerCase().contains(district.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    private boolean containsAnyStation(Flat item, ArrayList<String> stations) {
        for (String stationSearch : stations) {
            for (String stationFlat : item.getStations()) {
                if (stationFlat.toLowerCase().contains(stationSearch.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

}
