package tech.itpark.controller;

import org.springframework.web.bind.annotation.*;
import tech.itpark.manager.FlatManager;
import tech.itpark.model.Flat;

import java.util.ArrayList;

@RestController
public class FlatController {
    private FlatManager manager = new FlatManager(100_000.0, 1);

    @RequestMapping("/flats")
    public ArrayList<Flat> getAll(){
        return manager.getAll();
    }

    @RequestMapping("/flats/{id}")
    public Flat getById(@PathVariable int id){
        return manager.getFlatById(id);
    }

    @RequestMapping("/flats/{id}/save")
    public Flat save(@PathVariable int id,
                     @RequestParam int room,
                     @RequestParam int floor,
                     @RequestParam int price,
                     @RequestParam String district,
                     @RequestParam ArrayList<String> stations){
        return manager.save(new Flat(id, room, floor, price, district, stations));
    }

    @RequestMapping("/flats/{id}/remove")
    public Flat removeById(@PathVariable int id){
        return manager.removeById(id);
    }

    @RequestMapping("/flats/search")
    public ArrayList<Flat> search(@RequestParam String text){
        return manager.search(text, true, true);
    }

    @RequestMapping("/flats/search/{detail}")
    public ArrayList<Flat> search(@PathVariable String detail, @RequestParam String text){
        boolean searchByDistrict = false;
        boolean searchByStation = false;
        if (detail.contentEquals("district")){
            searchByDistrict = true;
        }
        if (detail.contentEquals("station")){
            searchByStation = true;
        }
        return manager.search(text, searchByDistrict, searchByStation);
    }

    @RequestMapping("/flats/filter")
    public ArrayList<Flat> filer(@RequestParam int rooms,
                                 @RequestParam int minprice,
                                 @RequestParam int maxprice,
                                 @RequestParam int minfloor,
                                 @RequestParam int maxfloor,
                                 @RequestParam ArrayList<String> districts,
                                 @RequestParam ArrayList<String> stations){
        return manager.filter(rooms, minprice, maxprice, minfloor, maxfloor, districts, stations);
    }
}
