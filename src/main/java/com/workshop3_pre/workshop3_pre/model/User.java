package com.workshop3_pre.workshop3_pre.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// @Data
// @AllArgsConstructor
// @NoArgsConstructor
// @Builder

public class User {


    
    private String id;

    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    @Pattern(regexp = "^[a-zA-Z\s]+$",message = "Name cannot contain numbers")
    @Size(min = 3, max = 64, message = "Name must be inbetween 3 and 64 characters")
    private String name;

    @NotNull
    @NotEmpty(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @Pattern(regexp = "^[0-9]*$",message = "Please enter a valid number")
    @Size(min=7,message = "Phone number must be more than 7 digits")
    private String phoneNumber;

    @Past(message = "Past dates only")
    @NotNull(message = "You must set your date of birth")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    public User(String name, String email, String phoneNumber, LocalDate dateOfBirth) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

    private Long dateOfBirthEpoch;


    public User() {
    }

    


    public User(String id,
            @NotNull(message = "Name cannot be null") @NotEmpty(message = "Name cannot be empty") @Size(min = 3, max = 64, message = "Name must be inbetween 3 and 64 characters") String name,
            @NotNull @NotEmpty(message = "Email is required") @Email(message = "Please provide a valid email address") String email,
            @Size(min = 7, message = "Phone number must be more than 7 digits") String phoneNumber,
            @Past(message = "Past dates only") @NotNull(message = "You must set your date of birth") LocalDate dateOfBirth) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

    


    public User(String id,
            @NotNull(message = "Name cannot be null") @NotEmpty(message = "Name cannot be empty") @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name cannot contain numbers") @Size(min = 3, max = 64, message = "Name must be inbetween 3 and 64 characters") String name,
            @NotNull @NotEmpty(message = "Email is required") @Email(message = "Please provide a valid email address") String email,
            @Pattern(regexp = "^[0-9]*$", message = "Please enter a valid number") @Size(min = 7, message = "Phone number must be more than 7 digits") String phoneNumber,
            @Past(message = "Past dates only") @NotNull(message = "You must set your date of birth") LocalDate dateOfBirth,
            Long dateOfBirthEpoch) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.dateOfBirthEpoch = dateOfBirthEpoch;
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    public Long getDateOfBirthEpoch() {
        return dateOfBirthEpoch;
    }


    public void setDateOfBirthEpoch(Long dateOfBirthEpoch) {
        this.dateOfBirthEpoch = dateOfBirthEpoch;
    }

    

    
}
