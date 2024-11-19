package com.workshop3_pre.workshop3_pre.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.workshop3_pre.workshop3_pre.model.User;

@Service
public class Contacts {
    private static List<User> userList;
    
    
    final private String dataDir;
    @Autowired
    public Contacts(String dataDir) {
        this.dataDir = dataDir;
    }




    public void saveUser(User user) throws IOException{
        userList = new ArrayList<>();
        String random = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String randomString = generateRandom(random);
       // String uuid = UUID.randomUUID().toString().replace("-", "").substring(0,8);
        user.setId(randomString);
        userList.add(user);
        System.out.println(dataDir);
        
        FileWriter writer = new FileWriter(dataDir + File.separator+ user.getId() + ".txt",false);
        BufferedWriter bw = new BufferedWriter(writer);
        bw.write(user.getName());
        bw.newLine();
        bw.write(user.getEmail());
        bw.newLine();
        bw.write(user.getPhoneNumber());
        bw.newLine();
        bw.write(String.valueOf(user.getDateOfBirth()));
        bw.newLine();
        bw.flush();
        bw.close();
        writer.close();

    }
    public User getUserById(String id) {
        for (User user : userList) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
                return null;
    }
    

    public List<User> getUsers() throws IOException, ParseException {
        userList = new ArrayList<>();

        File directory = new File(dataDir);
        File[] files = directory.listFiles();

        //string to localdate. which can be used directly in thymeleaf form
        // String dateString = "1998-12-17";
        // LocalDate date = LocalDate.parse(dateString);

        //string to long
        // String dob = "18 Dec 1975 10:25:00.000 SGT";
        // SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss.SSS zzz");
        // Date dDob = sdf.parse(dob);
        // Long epochDob = dDob.getTime();
        
        for (File file : files) {
            String fileName = file.getName();
            String[] tokens = fileName.split("\\.");
            String userFileId = tokens[0];
            //System.out.println(userFileId);
            
            User user = new User();
            user.setId(userFileId);
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            int count = 0;
           
            while ((line =br.readLine())!=null) {
                if (count == 0) {
                    user.setName(line);
                } else if (count == 1) {
                    user.setEmail(line);
                } else if (count==2) {
                    user.setPhoneNumber(line);
                } else if (count ==3) {


                    LocalDate date = LocalDate.parse(line, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    user.setDateOfBirth(date);
                    
                    //doesnt work
                    // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    // Date date = sdf.parse(line);
                    // user.setDateOfBirth(date);


                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                    Date dDob = sdf2.parse(line);
                    Long epochDob = dDob.getTime();
                    user.setDateOfBirthEpoch(epochDob);

                }
                count++;
                

            }
            
            // user.setId(userFileId);
            // user.setName("JONNN");
            // user.setEmail("jon@gmail.com");
            // user.setDateOfBirth(date);

            userList.add(user);
            

            //System.out.println(userFileId);
        }

        
        
        return userList;
    }

    public String generateRandom(String aToZ) {
    Random rand=new Random();
    StringBuilder res=new StringBuilder();
    for (int i = 0; i < 8; i++) {
       int randIndex=rand.nextInt(aToZ.length()); 
       res.append(aToZ.charAt(randIndex));            
    }
    return res.toString();
}
    
    
    
    
}
