package com.findjob.findjobbackend.service.city;

import com.findjob.findjobbackend.dto.request.SignUpForm;
import com.findjob.findjobbackend.model.City;
import com.findjob.findjobbackend.service.IGeneralService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ICityService  {

    City updateCity(City city);

    void getCityById(Long id);

    Iterable<City> findAll();

    Page<City> findAll(Pageable pageable);

    City save(City city);

    Optional<City> findById(Long id);

}
