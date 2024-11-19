package com.workshop3_pre.workshop3_pre;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Workshop3PreApplication {


    
    //mvn clean package spring-boot:run -Dspring-boot.run.arguments="--dataDir=opt/tmp/data"
	public static void main(String[] args) throws IOException {
		SpringApplication.run(Workshop3PreApplication.class, args);
		ApplicationArguments cliOpts = new DefaultApplicationArguments(args);
        String dataDir = "tmp/default/data";
		if (cliOpts.containsOption("dataDir")) {
            // Retrieve dataDir option
            dataDir = cliOpts.getOptionValues("dataDir").get(0);
            
            //System.out.println("Data Directory specified: " + dataDir);

            // Create a Path object for the data directory
            Path path = Paths.get(dataDir);

            // Check if the directory exists, and create it if it doesn't
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                System.out.println("Directory created at: " + path.toAbsolutePath());
            } else {
                System.out.println("Directory already exists at: " + path.toAbsolutePath());
            }

            

        } else {
            // Error message if --dataDir is not specified
            
            System.err.println("Error: The --dataDir option is required.");
            
            System.exit(-1);
            
        }
    }
    @Bean
    public String dataDir(ApplicationArguments args) {
        if (args.containsOption("dataDir")) {
            return args.getOptionValues("dataDir").get(0);
        } else {
            // Default value for tests or fallback
            return "/tmp/default/data";
        }
        //return args.getOptionValues("dataDir").get(0);
    }
    

}


