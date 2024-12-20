package com.workshop3_pre.workshop3_pre.repo;

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
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.workshop3_pre.workshop3_pre.model.User;

@Repository
public class ContactsRepo {


    private static List<User> userList;

    @Autowired
    final private String dataDir;
    public ContactsRepo(String dataDir,List<User> userList) throws IOException, ParseException {
        this.dataDir = dataDir;
        // userList = getUsers();

    }
    // @PostConstruct
    // public void init() {
    //     try {
    //         userList = getUsers(); // Populate userList
    //     } catch (IOException | ParseException e) {
    //         e.printStackTrace();
    //         userList = new ArrayList<>(); // Fallback to an empty list
    //     }
    // }

    public void saveUser(User user) throws IOException{
        userList = new ArrayList<>();
        userList.add(user);
    
        
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
        bw.write(String.valueOf(user.getDateOfBirth2()));
        bw.newLine();
        bw.flush();
        bw.close();
        writer.close();

    }
    
    public User getUserById(String id) {
        // for (User user : userList) {
        //     if (user.getId().equals(id)) {
        //         return user;
        //     }
        // }
        //         return null;

        User foundUser = userList.stream()
                                .filter(u -> u.getId().equals(id))
                                .findFirst()
                                .get();
        return foundUser;
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
                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

                    Date dDob = sdf2.parse(line);
                    Long epochDob = dDob.getTime();
                    user.setDateOfBirthEpoch(epochDob);
                    user.setDateOfBirth2(dDob);
                }
                    //doesnt work
                    // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    // Date date = sdf.parse(line);
                    // user.setDateOfBirth(date);

                // } else if (count==4) {
                //     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                //     Date date2 = sdf.parse(line);
                //     user.setDateOfBirth2(date2);

                    
                // }
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

    public Boolean deleteUserById(String id) {
        //find first
        
        User userToDelete = userList.stream()
                                    .filter(u -> u.getId().equals(id))
                                    .findFirst()
                                    .get();
        //delete
        if (userToDelete == null) {
            return false;
        } else {

            userList.remove(userToDelete);


            File directory = new File(dataDir);
            File[] files = directory.listFiles();

            for (File file : files) {
                String fileName = file.getName();
                String[] tokens = fileName.split("\\.");
                String userFileId = tokens[0];
                if (userFileId.equals(id)) {
                    file.delete();
                }

            }

            return true;

        }
        
    }
    public Boolean updateUser(User user) throws IOException {
        //find first
        List<User> filteredUser = userList.stream()
                                          .filter(u->u.getId().equals(user.getId()))
                                          .collect(Collectors.toList());
        if (filteredUser.size()>0) {
            userList.remove(filteredUser.getFirst());
            userList.add(user);

            File directory = new File(dataDir);
            File[] files = directory.listFiles();
            File tempFile = new File(dataDir + File.separator+ user.getId() + "_temp.txt");
            FileWriter writer = new FileWriter(tempFile,false);
            BufferedWriter bw = new BufferedWriter(writer);

            for (File file : files) {
                String fileName = file.getName();
                String[] tokens = fileName.split("\\.");
                String userFileId = tokens[0];
                if (userFileId.equals(user.getId())) {
                    file.delete();
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
                    tempFile.renameTo(file);

                    }
            }

            return true;
        }
        return false;
    }
    public Boolean checkIfNameExists(String name) {

        for (User user : userList) {
            if(user.getName().equals(name))
            return true;
        }
        return false;

    }

    public Boolean checkIfEmailExists(String email) {

        for (User user : userList) {
            if(user.getEmail().equals(email))
            return true;
        }
        return false;

    }
    
}
