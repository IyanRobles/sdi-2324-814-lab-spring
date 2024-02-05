package com.uniovi.sdi2324814spring.controllers;

import com.uniovi.sdi2324814spring.entities.Professor;
import com.uniovi.sdi2324814spring.services.ProfessorsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfessorsController {

    private final ProfessorsService professorsService;

    public ProfessorsController(ProfessorsService professorsService) {
        this.professorsService = professorsService;
    }

    @RequestMapping("/professor/list")
    public String getList(Model model) {
        model.addAttribute("professorList", professorsService.getProfessors());
        return "professor/list";
    }
    @RequestMapping(value = "/professor/add")
    public String getProfessor() {
        return "professor/add";
    }
    @RequestMapping(value = "/professor/add", method = RequestMethod.POST)
    public String setProfessor(@ModelAttribute Professor professor) {
        professorsService.addProfessor(professor);
        return "redirect:/professor/list";
    }
    @RequestMapping("/professor/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("professor", professorsService.getProfessor(id));
        return "professor/details";
    }
    @RequestMapping("/professor/delete/{id}")
    public String deleteProfessor(@ModelAttribute Professor professor, @PathVariable Long id){
        professorsService.deleteProfessor(id);
        return "redirect:/professor/list";
    }
}
