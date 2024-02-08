package com.uniovi.sdi2324814spring.services;

import javax.annotation.PostConstruct;

import com.uniovi.sdi2324814spring.entities.User;
import com.uniovi.sdi2324814spring.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
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
        usersRepository.save(user);
    }
    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }
}