package com.findjob.findjobbackend.service.vacancies;

import com.findjob.findjobbackend.model.Vacancies;
import com.findjob.findjobbackend.repository.IVacanciesRepository;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@AllArgsConstructor
public class VacanciesService implements IVacanciesService {

    private final IVacanciesRepository vacanciesRepository;



    @Override
    public Iterable<Vacancies> findAll() {
        return vacanciesRepository.findAll();
    }

    @Override
    public Page<Vacancies> findAll(Pageable pageable) {
        return vacanciesRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        vacanciesRepository.deleteById(id);
    }

    @Override
    public Vacancies save(Vacancies vacancies) {
        return vacanciesRepository.save(vacancies);
    }

    @Override
    public Optional<Vacancies> findById(Long id) {
        return vacanciesRepository.findById(id);
    }

}
