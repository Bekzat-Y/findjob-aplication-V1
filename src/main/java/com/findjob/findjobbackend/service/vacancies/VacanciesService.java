package com.findjob.findjobbackend.service.vacancies;

import com.findjob.findjobbackend.model.Vacancies;
import com.findjob.findjobbackend.repository.IVacanciesRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VacanciesService implements IVacanciesService {

    private static final Logger logger = LoggerFactory.getLogger(VacanciesService.class);

    private final IVacanciesRepository vacanciesRepository;

    @Override
    public Iterable<Vacancies> findAll() {
        return vacanciesRepository.findAll();
    }

    @Override
    public Page<Vacancies> findAll(Pageable pageable) {
        if (pageable == null) {
            logger.error("Pageable parameter is null");
            throw new IllegalArgumentException("Pageable parameter cannot be null");
        }
        return vacanciesRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            logger.error("ID parameter is null");
            throw new IllegalArgumentException("ID parameter cannot be null");
        }
        vacanciesRepository.deleteById(id);
    }

    @Override
    public Vacancies save(Vacancies vacancies) {
        if (vacancies == null) {
            logger.error("Vacancies parameter is null");
            throw new IllegalArgumentException("Vacancies parameter cannot be null");
        }
        return vacanciesRepository.save(vacancies);
    }

    @Override
    public Optional<Vacancies> findById(Long id) {
        if (id == null) {
            logger.error("ID parameter is null ");
            throw new IllegalArgumentException("ID parameter cannot be null");
        }
        return vacanciesRepository.findById(id);
    }
}
