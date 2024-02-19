package com.uniovi.sdi2324814spring.services;

import javax.annotation.PostConstruct;

import com.uniovi.sdi2324814spring.entities.Mark;
import com.uniovi.sdi2324814spring.entities.User;
import com.uniovi.sdi2324814spring.repositories.UsersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public UsersService(UsersRepository usersRepository, BCryptPasswordEncoder
            bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @PostConstruct
    public void init() {
    }
    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        usersRepository.findAll().forEach(users::add);
        return users;
    }
    public User getUser(Long id) {
        return usersRepository.findById(id).get();
    }
    public void addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    public User getUserByDni(String dni) {
        return usersRepository.findByDni(dni);
    }

    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }

    public void updateUser(long id, User newUser) {
        if (newUser.equals(null))
            return;
        User originalUser = getUser(id);
        originalUser.setDni(newUser.getDni());
        originalUser.setName(newUser.getName());
        originalUser.setLastName(newUser.getLastName());
        usersRepository.save(originalUser);
    }

    public Page<User> getUsers(Pageable pageable) {
        Page<User> users = new PageImpl<>(new LinkedList<User>());
        users = usersRepository.findAll(pageable);
        return users;
    }
    public Page<User> searchUsersByName(Pageable pageable, String search) {
        Page<User> users = new PageImpl<User>(new LinkedList<User>());
        search = "%"+search+"%";
        users = usersRepository.searchByName(pageable, search);
        return users;
    }
}