package com.uniovi.sdi2324814spring.repositories;

import com.uniovi.sdi2324814spring.entities.Mark;
import com.uniovi.sdi2324814spring.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {
    User findByDni(String dni);

    @Query("SELECT r FROM User r ORDER BY r.id ASC")
    Page<User> findAll(Pageable pageable);

    @Query("SELECT r FROM User r WHERE (LOWER(r.name) LIKE LOWER(?1) OR LOWER(r.lastName) LIKE LOWER(?1))")
    Page<User> searchByName(Pageable pageable, String searchtext);
}
