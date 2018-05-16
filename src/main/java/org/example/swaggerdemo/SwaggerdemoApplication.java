package org.example.swaggerdemo;

import org.example.swaggerdemo.entity.Company;
import org.example.swaggerdemo.entity.Employee;
import org.example.swaggerdemo.repository.CompanyRepository;
import org.example.swaggerdemo.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@SpringBootApplication
public class SwaggerdemoApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerdemoApplication.class);

    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    public SwaggerdemoApplication(EmployeeRepository employeeRepository, CompanyRepository companyRepository) {
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(SwaggerdemoApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) {

        List<Employee> savedEmployees = employeeRepository.saveAll(Arrays.asList(
                Employee.builder()
                        .dateOfBirth(LocalDate.of(1954, Month.APRIL, 7))
                        .firstname("Jackie")
                        .lastname("Chan")
                        .build(),
                Employee.builder()
                        .dateOfBirth(LocalDate.of(1952, Month.JUNE, 7))
                        .firstname("Liam")
                        .lastname("Neeson")
                        .build(),
                Employee.builder()
                        .dateOfBirth(LocalDate.of(1946, Month.JULY, 6))
                        .firstname("Sylvester")
                        .lastname("Stallone")
                        .build(),
                Employee.builder()
                        .dateOfBirth(LocalDate.of(1955, Month.MARCH, 19))
                        .firstname("Bruce")
                        .lastname("Willis")
                        .build()));

        Company company = Company.builder()
                .name("Global Security Agency")
                .employees(new HashSet<>(savedEmployees))
                .build();

        companyRepository.save(company);

        List<Company> savedCompany = companyRepository.findAll();
        LOGGER.info("Saved company in db: " + savedCompany);
    }
}
