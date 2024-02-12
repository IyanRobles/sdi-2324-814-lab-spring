package com.uniovi.sdi2324814spring.validators;

import com.uniovi.sdi2324814spring.entities.Mark;
import com.uniovi.sdi2324814spring.entities.User;
import com.uniovi.sdi2324814spring.services.MarksService;
import com.uniovi.sdi2324814spring.services.UsersService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MarkFormValidator implements Validator {
    private final MarksService marksService;
    public MarkFormValidator(MarksService marksService) {
        this.marksService = marksService;
    }
    @Override
    public boolean supports(Class<?> aClass) {
        return Mark.class.equals(aClass);
    }
    @Override
    public void validate(Object target, Errors errors) {
        Mark mark = (Mark) target;
        if (mark.getScore() < 0 || mark.getScore() > 10) {
            errors.rejectValue("score", "Error.mark.score.value");}
        if (mark.getDescription().length() < 20) {
            errors.rejectValue("description", "Error.mark.description.length");}
    }
}
