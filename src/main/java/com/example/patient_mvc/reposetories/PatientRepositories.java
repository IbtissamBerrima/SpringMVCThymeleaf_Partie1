package com.example.patient_mvc.reposetories;

import com.example.patient_mvc.entites.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepositories extends JpaRepository<Patient,Long> {

    //methode de pageni
    Page<Patient> findByNomContains(String kw, Pageable pageable);
}
