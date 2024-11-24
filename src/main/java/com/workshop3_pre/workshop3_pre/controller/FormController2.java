package com.workshop3_pre.workshop3_pre.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.workshop3_pre.workshop3_pre.model.User;
import com.workshop3_pre.workshop3_pre.service.impl.Contacts;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/multimap")
public class FormController2 {

    final private String dataDir;
    private Contacts contacts;
    @Autowired
    public FormController2(Contacts contacts,String dataDir) {
        this.contacts = contacts;
        this.dataDir = dataDir;
    }


    @GetMapping("")
    public String createStudentForm(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "index2";
    }

    

    @PostMapping("/contact")
    public String contactPage(@Valid @RequestBody MultiValueMap<String,String> form,BindingResult result,Model model,
    RedirectAttributes redirectAttributes,HttpServletResponse response) throws IOException {
        if (result.hasErrors()) {
            return "index2";
        }
        User user = new User();
        String name = form.getFirst("name");
        String email = form.getFirst("email");
        String phoneNumber = form.getFirst("phoneNumber");
        String dateOfBirth = form.getFirst("dateOfBirth");
        LocalDate date = LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        user.setName(name);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setDateOfBirth(date);
        
        model.addAttribute("user",user);
        contacts.saveUser(user);

        redirectAttributes.addFlashAttribute("message","User created successfully!");
        
       
        return "redirect:/success";
    }


    @GetMapping("/success")
    public String successPage() {
        return "success_page";
    }

    @GetMapping("/contact/{id}")
    public String individualContactPage(@PathVariable String id,Model model){
        String fileName = id + ".txt";
        Path filePath = Paths.get(dataDir,fileName);
        if (!Files.exists(filePath)) {
            return "404_page";
        }
        User user = contacts.getUserById(id);
        model.addAttribute("user", user);
        System.out.println(user.getName());
        

        return "individual_contact_page";

    }

    @GetMapping("/contacts")
    public String contactsPage(Model model) throws IOException, ParseException{
       
        List<User> userList = contacts.getUsers();
        
        model.addAttribute("userList",userList);
        return "contacts_page";
    }

    @GetMapping("/delete/{userid}")
    public String deleteUser(@PathVariable("userid") String id) {
        contacts.deleteUserById(id);
        return "redirect:/contacts";
    }

    @GetMapping("/update/{userid}")
    public String updateUser(@PathVariable("userid") String id,Model model) {
        User userToUpdate = contacts.getUserById(id);
        model.addAttribute("user",userToUpdate);
        return "updateuser_page2";
    }

    @PostMapping("/update")
    public String updatedUserData(@Valid @RequestBody MultiValueMap<String,String> form,BindingResult result,Model model) throws IOException {
        if (result.hasErrors()) {
            return "updateuser_page2";
        }
        User user = new User();
        String name = form.getFirst("name");
        String email = form.getFirst("email");
        String phoneNumber = form.getFirst("phoneNumber");
        String dateOfBirth = form.getFirst("dateOfBirth");
        LocalDate date = LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        user.setName(name);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setDateOfBirth(date);
        
        model.addAttribute("user",user);
        contacts.updateUser(user);
        return "redirect:/contacts";
    }
    


     
    
}
