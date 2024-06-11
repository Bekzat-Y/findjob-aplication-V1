package com.findjob.findjobbackend.service.city;

import com.findjob.findjobbackend.model.City;
import com.findjob.findjobbackend.repository.ICityRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CityService implements ICityService {
    private static final Logger logger = LoggerFactory.getLogger(CityService.class);
    private final ICityRepository cityRepository;

    @Override
    public Iterable<City> findAll() {
        return cityRepository.findAll();
    }

    @Override
    public Page<City> findAll(Pageable pageable) {
        if (pageable == null) {
            logger.error("Pageable parameter is null");
            throw new IllegalArgumentException("Pageable parameter cannot be null");
        }
        return cityRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            logger.error("ID parameter is null ");
            throw new IllegalArgumentException("ID parameter cannot be null");
        }
        if (!cityRepository.existsById(id)) {
            logger.error("City with ID {} does not exist", id);
            throw new IllegalArgumentException("City with ID " + id + " does not exist");
        }
        cityRepository.deleteById(id);
    }

    @Override
    public City save(City city) {
        if (city == null) {
            logger.error("City parameter is null");
            throw new IllegalArgumentException("City parameter cannot be null");
        }
        return cityRepository.save(city);
    }

    @Override
    public Optional<City> findById(Long id) {
        if (id == null) {
            logger.error("ID parameter is null");
            throw new IllegalArgumentException("ID parameter cannot be null");
        }
        return cityRepository.findById(id);
    }
}
