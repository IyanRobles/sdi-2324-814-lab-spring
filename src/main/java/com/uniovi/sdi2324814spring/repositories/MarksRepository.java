package com.uniovi.sdi2324814spring.repositories;

import com.uniovi.sdi2324814spring.entities.Mark;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarksRepository extends CrudRepository<Mark, Long> {

}