package com.uniovi.sdi2324814spring.controllers;

import com.uniovi.sdi2324814spring.entities.Professor;
import com.uniovi.sdi2324814spring.services.ProfessorsService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProfessorsController {

    private final ProfessorsService professorsService;

    public ProfessorsController(ProfessorsService professorsService) {
        this.professorsService = professorsService;
    }

    @RequestMapping("/professor/list")
    public String getList() {
        return professorsService.getProfessors().toString();
    }
    @RequestMapping(value = "/professor/add", method = RequestMethod.POST)
    public String setMark(@ModelAttribute Professor professor) {
        professorsService.addProfessor(professor);
        return "Added professor: " + professor.toString();
    }
    @RequestMapping("/professor/details/{id}")
    public String getDetail(@PathVariable Long id) {
        return professorsService.getProfessor(id).toString();
    }
    @RequestMapping("/professor/delete/{id}")
    public String deleteMark(@PathVariable Long id) {
        professorsService.deleteProfessor(id);
        return "Professor removed";
    }
}
