package com.uniovi.sdi2324814spring.controllers;

import com.uniovi.sdi2324814spring.entities.Mark;
import com.uniovi.sdi2324814spring.entities.Professor;
import com.uniovi.sdi2324814spring.entities.User;
import com.uniovi.sdi2324814spring.services.ProfessorsService;
import com.uniovi.sdi2324814spring.validators.ProfessorFormValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfessorsController {

    private final ProfessorsService professorsService;
    private final ProfessorFormValidator professorFormValidator;

    public ProfessorsController(ProfessorsService professorsService, ProfessorFormValidator professorFormValidator) {
        this.professorsService = professorsService;
        this.professorFormValidator = professorFormValidator;
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

    @RequestMapping(value = "/professor/add", method = RequestMethod.GET)
    public String setProfessor(Model model) {
        model.addAttribute("professor", new Professor());
        return "professor/add";
    }
    @RequestMapping(value = "/professor/add", method = RequestMethod.POST)
    public String setProfessor(@Validated Professor professor, BindingResult result) {
        professorFormValidator.validate(professor, result);
        if (result.hasErrors()) {
            return "professor/add";
        }

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
