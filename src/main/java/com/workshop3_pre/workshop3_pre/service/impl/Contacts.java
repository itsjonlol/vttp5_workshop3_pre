package com.workshop3_pre.workshop3_pre.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.workshop3_pre.workshop3_pre.model.User;
import com.workshop3_pre.workshop3_pre.repo.ContactsRepo;

@Service
public class Contacts {
    
    
   
    @Autowired
    ContactsRepo contactsRepo;

    public void saveUser(User user) throws IOException{
    
        contactsRepo.saveUser(user);

    }
    public User getUserById(String id) {
        
        return contactsRepo.getUserById(id);  
    }
    
    public List<User> getUsers() throws IOException, ParseException {
        
        return contactsRepo.getUsers();
    }
    public Boolean deleteUserById(String id) {
        return contactsRepo.deleteUserById(id);
        
    }
    public Boolean updateUser(User user) throws IOException {
        return contactsRepo.updateUser(user);
    }

    public Boolean checkIfNameExists(String name) {

        return contactsRepo.checkIfNameExists(name);

    }

    public Boolean checkIfEmailExists(String email) {
        return contactsRepo.checkIfEmailExists(email);
    }

    
    
    
    
    
}
