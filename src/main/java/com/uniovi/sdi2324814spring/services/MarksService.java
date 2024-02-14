package com.uniovi.sdi2324814spring.services;

import com.uniovi.sdi2324814spring.entities.Mark;
import com.uniovi.sdi2324814spring.repositories.MarksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class MarksService {

    @Autowired
    private MarksRepository marksRepository;

    /* Inyección de dependencias basada en constructor (opción recomendada)*/
    private final HttpSession httpSession;
    @Autowired
    public MarksService(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    /*
    private List<Mark> marksList = new LinkedList<>();
    @PostConstruct
    public void init() {
    marksList.add(new Mark(1L, "Ejercicio 1", 10.0));
    marksList.add(new Mark(2L, "Ejercicio 2", 9.0));
    } */

    public List<Mark> getMarks() {
        List<Mark> marks = new ArrayList<Mark>();
        marksRepository.findAll().forEach(marks::add);
        return marks;
    }
    public Mark getMark(Long id) {
        Set<Mark> consultedList = (Set<Mark>) httpSession.getAttribute("consultedList");
        if (consultedList == null) {
            consultedList = new HashSet<>();
        }
        Mark mark = marksRepository.findById(id).isPresent() ? marksRepository.findById(id).get() : new Mark();
        consultedList.add(mark);
        httpSession.setAttribute("consultedList", consultedList);
        return mark;
    }
    public void addMark(Mark mark) {
// Si en Id es null le asignamos el último + 1 de la lista
        marksRepository.save(mark);
    }
    public void deleteMark(Long id) {
        marksRepository.deleteById(id);
    }
}