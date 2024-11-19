package com.workshop3_pre.workshop3_pre.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.workshop3_pre.workshop3_pre.model.User;
import com.workshop3_pre.workshop3_pre.service.impl.Contacts;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class FormController {

    final private String dataDir;
    private Contacts contacts;
    @Autowired
    public FormController(Contacts contacts,String dataDir) {
        this.contacts = contacts;
        this.dataDir = dataDir;
    }


    @GetMapping("/")
    public String createStudentForm(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "index";
    }

    
    @PostMapping("/contact")
    public String contactPage(@Valid @ModelAttribute("user") User user,BindingResult result,Model model,
    RedirectAttributes redirectAttributes,HttpServletResponse response) throws IOException {
        if (result.hasErrors()) {
            return "index";
        }
        model.addAttribute("user",user);
        //user.setId("TestID");
        //System.out.println(user.getId());
        contacts.saveUser(user);


        redirectAttributes.addFlashAttribute("message","User created successfully!");
        
        // FileWriter writer = new FileWriter("/opt/tmp/data/test.txt");
        // BufferedWriter bw = new BufferedWriter(writer);
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
        

        // File directory = new File(dataDir);
        // File[] files = directory.listFiles();
        // String dateString = "1998-12-17";
        // LocalDate date = LocalDate.parse(dateString);
        // for (File file : files) {
        //     String fileName = file.getName();
        //     String[] tokens = fileName.split("\\.");
        //     String userFileId = tokens[0];
        //     //System.out.println(userFileId);
            
        //     User user = new User();
        //     user.setId(userFileId);
        //     FileReader reader = new FileReader(file);
        //     BufferedReader br = new BufferedReader(reader);
        //     String line = "";
        //     int count = 0;
           
        //     while ((line =br.readLine())!=null) {
        //         if (count == 0) {
        //             user.setName(line);
        //         } else if (count == 1) {
        //             user.setEmail(line);
        //         } else if (count==2) {
        //             user.setPhoneNumber(line);
        //         } else if (count ==3) {
        //             LocalDate date = LocalDate.parse(line);
        //             user.setDateOfBirth(date);
        //         }
        //         count++;
                

        //     }
            
        //     // user.setId(userFileId);
        //     // user.setName("JONNN");
        //     // user.setEmail("jon@gmail.com");
        //     // user.setDateOfBirth(date);

        //     userList.add(user);
            

        //     //System.out.println(userFileId);
        // }
        
        
        
        
        model.addAttribute("userList",userList);
        return "contacts_page";
    }


     
    
}
