package com.uniovi.sdi2324814spring.repositories;

import com.uniovi.sdi2324814spring.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {
}
