package com.uniovi.sdi2324814spring.services;

import com.uniovi.sdi2324814spring.entities.Professor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProfessorsService {

    private List<Professor> professorsList = new LinkedList<>();

    @PostConstruct
    public void init() {
        professorsList.add(new Professor(1L, "12345678A", "Nombre1", "Apellido1", "Catedratico"));
        professorsList.add(new Professor(2L, "87654321B", "Nombre2", "Apellido2", "Titular"));
    }

    public List<Professor> getProfessors() {
        return new ArrayList<>(professorsList);
    }
    public Professor getProfessor(Long id) {
        return professorsList.stream()
                .filter(professor -> professor.getId().equals(id)).findFirst().get();
    }
    public void addProfessor(Professor professor) {
        // Si en Id es null le asignamos el ultimo + 1 de la lista
        if (professor.getId() == null) {
            professor.setId(professorsList.get(professorsList.size() - 1).getId() + 1);
        }
        professorsList.add(professor);
    }
    public void deleteProfessor(Long id) {
        professorsList.removeIf(professor -> professor.getId().equals(id));
    }

}
