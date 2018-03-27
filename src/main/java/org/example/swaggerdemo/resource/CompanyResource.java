package org.example.swaggerdemo.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.example.swaggerdemo.entity.Company;
import org.example.swaggerdemo.entity.Employee;
import org.example.swaggerdemo.repository.CompanyRepository;
import org.example.swaggerdemo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Api(value = CompanyResource.RESOURCE_BASE_URI)
@RestController
@RequestMapping(CompanyResource.RESOURCE_BASE_URI)
public class CompanyResource {

    static final String RESOURCE_BASE_URI = "companies";

    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public CompanyResource(CompanyRepository companyRepository,
                           EmployeeRepository employeeRepository) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    @ApiOperation(value = "Find all companies", response = Company.class, responseContainer = "List")
    @GetMapping
    public ResponseEntity<List<Company>> findAll() {
        return ResponseEntity.ok(companyRepository.findAll());
    }

    @ApiOperation(value = "Add employee to company")
    @PostMapping("{companyId}/employees/{employeeId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee was added"),
            @ApiResponse(code = 400, message = "Employee or company was not found")
    })
    public ResponseEntity addEmployee(@PathVariable("companyId") Long companyId, @PathVariable("employeeId") Long employeeId) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if (!optionalCompany.isPresent()) {
            return ResponseEntity.badRequest().body("Company with id=" + companyId + " not found");
        }

        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (!optionalEmployee.isPresent()) {
            return ResponseEntity.badRequest().body("Employee with id=" + employeeId + " not found");
        }

        Company company = optionalCompany.get();
        company.getEmployees().add(optionalEmployee.get());
        companyRepository.save(company);

        return ResponseEntity.ok(company);
    }

    @ApiOperation(value = "Remove employee from company")
    @DeleteMapping("{companyId}/employees/{employeeId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee was added"),
            @ApiResponse(code = 400, message = "Employee or company was not found"),
            @ApiResponse(code = 422, message = "Employee does not belong to company")
    })
    public ResponseEntity removeEmployee(@PathVariable("companyId") Long companyId, @PathVariable("employeeId") Long employeeId) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if (!optionalCompany.isPresent()) {
            return ResponseEntity.badRequest().body("Company with id=" + companyId + " not found");
        }

        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (!optionalEmployee.isPresent()) {
            return ResponseEntity.badRequest().body("Employee with id=" + employeeId + " not found");
        }

        Company company = optionalCompany.get();
        boolean employeeRemoved = company.getEmployees().remove(optionalEmployee.get());
        if (employeeRemoved) {
            companyRepository.save(company);
            return ResponseEntity.ok(company);
        } else {
            return ResponseEntity.unprocessableEntity().body("Employee with id=" + employeeId + " does not belong to company with id=" + companyId);
        }
    }
}

