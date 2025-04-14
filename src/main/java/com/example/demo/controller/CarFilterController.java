package com.example.demo.controller;

import com.example.demo.model.Car;
import com.example.demo.service.CarFilterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarFilterController {

    private final CarFilterService carFilterService;

    CarFilterController(CarFilterService carFilterService) {
        this.carFilterService = carFilterService;
    }

    @GetMapping("/filter")
    public List<Car> filterCars(@RequestParam(required = false) String brand,
                                @RequestParam(required = false) Integer year,
                                @RequestParam(required = false) String color
    ){
        return carFilterService.filterCars(brand,year,color);
    }

    @PostMapping("/add")
    public ResponseEntity<Car> addACar(@RequestBody Car car) {
        return ResponseEntity.ok(carFilterService.addCar(car));
    }
    @DeleteMapping
    public ResponseEntity<Car> deleteACar( @RequestParam Integer id) {
        return ResponseEntity.ok(carFilterService.deleteACar(id));
    }

    @GetMapping
    public List<Car> getAllCars() {
        return carFilterService.getAllCars();
    }



}
