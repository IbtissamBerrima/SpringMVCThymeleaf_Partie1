package com.example.patient_mvc.web;

import com.example.patient_mvc.entites.Patient;
import com.example.patient_mvc.reposetories.PatientRepositories;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.Binding;
import javax.validation.Valid;
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

    //Methode return view
    @GetMapping("/formPatients")
    public String formPatients(Model model){
        model.addAttribute("patient",new Patient());
        return "formPatients";
    }

    //Methode save "pour ajouter et edit"
    @PostMapping(path = "/save")
    public String save(Model model, @Valid Patient patient, BindingResult bindingResult,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "") String keyword){
       if (bindingResult.hasErrors()) return "formPatients";

        patientRepositories.save((patient));
        return "redirect:/index?page"+page+"&keyword="+keyword; //rederction vers index

        //Pour la validation il faut faire 3 anotation il faut aller sur class siez not emmpty ou niveau controller ajouter @valid et @bindingResult dans poom ajouter dependency et dans html il faut utiliser th:erreurs
    }

   //Edit patient
    @GetMapping("/editPatient")
    public String editPatient(Model model,Long id,String keyword, int page){
        //chercher id
        Patient patient=patientRepositories.findById(id).orElse(null);

        if (patient==null) throw  new RuntimeException("Patient introuvable");
        model.addAttribute("patient",patient);
        model.addAttribute("page",page);
        model.addAttribute("keyword",keyword);
        return "editPatient";
    }
}
