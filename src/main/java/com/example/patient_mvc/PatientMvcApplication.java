package com.example.patient_mvc;

import com.example.patient_mvc.entites.Patient;
import com.example.patient_mvc.reposetories.PatientRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class PatientMvcApplication {
    //Declaration de l'objet

    public static void main(String[] args) {
        SpringApplication.run(PatientMvcApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(PatientRepositories patientRepositories){
        return args ->{
           patientRepositories.save(new Patient(null,"Hassan",new Date(),false,10));
            patientRepositories.save(new Patient(null,"Mohamed",new Date(),true,20));
            patientRepositories.save(new Patient(null,"Yassemine",new Date(),false,30));

            patientRepositories.findAll().forEach(patient -> {
                        System.out.println(patient.getNom());

                    }
                    );
        };
    }
}
