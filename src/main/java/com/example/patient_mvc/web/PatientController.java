package com.example.patient_mvc.web;

import com.example.patient_mvc.entites.Patient;
import com.example.patient_mvc.reposetories.PatientRepositories;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {

    private PatientRepositories patientRepositories;

    //methode vue patients pour acceder a la methode @GetMapping
    @GetMapping(path = "/index")
    public String patients(Model model,
                           //Para de la methode spécifier la page et la size afficher
                           @RequestParam(name = "page",defaultValue = "0") int page,
                           @RequestParam(name = "size",defaultValue = "5") int size,
                           @RequestParam(name = "keyword",defaultValue = "") String keyword) {
        Page<Patient> pagePatients= (Page<Patient>) patientRepositories.findByNomContains(keyword,PageRequest.of(page,size));
        model.addAttribute("ListPatient",pagePatients.getContent());
        //stocke le nb de page , un tableau contient nb page
        model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
        //Pour afficher la page courant
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
          return "patient";
    }
    //methode delte
    @GetMapping("/delete")
    public String delete(Long id, String keyword, int page){
        patientRepositories.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }
  //Liste patient format jyson
    @GetMapping("/patients")
    @ResponseBody
    public List<Patient> listPatients(){
        return  patientRepositories.findAll();
    }
}
