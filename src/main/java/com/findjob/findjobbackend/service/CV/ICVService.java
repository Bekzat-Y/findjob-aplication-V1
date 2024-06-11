package com.findjob.findjobbackend.service.CV;


import com.findjob.findjobbackend.model.CV;
import com.findjob.findjobbackend.service.IGeneralService;

import java.util.Optional;

public interface ICVService extends IGeneralService<CV> {
    Boolean existsByUserId(Long idUser);
    Optional<CV> findByUserId(Long id);
}
