package com.example.demo.service;

import com.example.demo.model.Car;
import java.util.List;
import com.example.demo.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CarFilterService {

    private final CarRepository carRepository;

    @Autowired
    CarFilterService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car addCar(Car car) {
        return carRepository.save(car);
    }

    public List<Car> filterCars(String brand, Integer year, String color) {
        Specification<Car> spec = Specification.where(null);

        if (brand != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("brand"), brand));
        }
        if (year != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("year"), year));
        }
        if (color != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("color"), color));
        }
        return carRepository.findAll(spec);
    }

    public Car deleteACar(Integer id){
        return carRepository.deleteCarById(id);
    }
}

