package com.findjob.findjobbackend.controller;


import com.findjob.findjobbackend.dto.request.SignUpForm;
import com.findjob.findjobbackend.model.City;
import com.findjob.findjobbackend.service.city.CityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/cities")
@AllArgsConstructor
public class CityController {
    private final CityService cityService;


    @GetMapping("/showAll")
    public ResponseEntity<Iterable<City>> showAll() {
        return new ResponseEntity<>(cityService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/createCity")
    public ResponseEntity<City> createCity(@RequestBody City city) {
        return new ResponseEntity<>(cityService.save(city), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> findById(@PathVariable Long id) {
        Optional<City> cityOptional = cityService.findById(id);
        return cityOptional.map(city -> new ResponseEntity<>(city, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<City> editCity(@PathVariable Long id, @RequestBody City city) {
        Optional<City> cityOptional = cityService.findById(id);
        if (cityOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            if (id != null) {
                city.setId(id);
            }
            return new ResponseEntity<>(cityService.updateCity(city),HttpStatus.OK);
        }
    }
    @Transactional
    @GetMapping("/delete/{id}")
    public ResponseEntity<City> deleteCityById(@PathVariable Long id) {
        if (id != null) {
            cityService.getCityById(id);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>( HttpStatus.OK);
    }


}
