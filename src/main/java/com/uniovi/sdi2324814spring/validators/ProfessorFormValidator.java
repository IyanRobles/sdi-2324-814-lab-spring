package com.uniovi.sdi2324814spring.validators;

import com.uniovi.sdi2324814spring.entities.Mark;
import com.uniovi.sdi2324814spring.entities.Professor;
import com.uniovi.sdi2324814spring.services.MarksService;
import com.uniovi.sdi2324814spring.services.ProfessorsService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProfessorFormValidator implements Validator {
    private final ProfessorsService professorsService;
    public ProfessorFormValidator(ProfessorsService professorsService) {
        this.professorsService = professorsService;
    }
    @Override
    public boolean supports(Class<?> aClass) {
        return Professor.class.equals(aClass);
    }
    @Override
    public void validate(Object target, Errors errors) {
        Professor professor = (Professor) target;
        if (professor.getDni().length() != 9) {
            errors.rejectValue("dni", "Error.professor.dni.length");}
        else if (!Character.isLetter(professor.getDni().toCharArray()[8])) {
            errors.rejectValue("dni", "Error.professor.dni.letter");}
        if (professorsService.getProfessorByDni(professor.getDni()).isPresent()) {
            errors.rejectValue("dni", "Error.professor.dni.exists");}
        professor.setName(professor.getName().trim());
        professor.setSurname(professor.getSurname().trim());
        if (professor.getName().length() == 0) {
            errors.rejectValue("name", "Error.professor.name.empty");}
        if (professor.getSurname().length() == 0) {
            errors.rejectValue("surname", "Error.professor.surname.empty");}
    }
}
