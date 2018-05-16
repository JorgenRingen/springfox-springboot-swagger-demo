package org.example.swaggerdemo.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

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
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returning list of companies"),
    })
    @GetMapping
    public ResponseEntity<List<Company>> findAll() {
        return ResponseEntity.ok(companyRepository.findAll());
    }

    @ApiOperation(value = "Find company by id", response = Employee.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "Company id", paramType = "path")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Company was found"),
            @ApiResponse(code = 404, message = "Company not found")
    })
    @GetMapping("{id}")
    public ResponseEntity<Company> findById(@PathVariable final Long id) {
        final Optional<Company> company = companyRepository.findById(id);
        return company.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Create company")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Company was created"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @PostMapping
    public ResponseEntity<Long> post(@RequestBody final Company company, UriComponentsBuilder uri) {
        final Long id = companyRepository.save(company).getId();
        final URI path = uri.path(RESOURCE_BASE_URI + "/" + id).build().toUri();
        return ResponseEntity.created(path).build();
    }

    @ApiOperation(value = "Delete company")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "Company id", paramType = "path")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Company deleted"),
            @ApiResponse(code = 404, message = "Company not found")
    })
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable final Long id) {
        Optional<Company> companyToDelete = companyRepository.findById(id);
        if (companyToDelete.isPresent()) {
            employeeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Add employee to company")
    @PostMapping("{companyId}/employees")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Employee was added to company"),
            @ApiResponse(code = 400, message = "Employee doesn't exist or employee already belongs to company"),
            @ApiResponse(code = 404, message = "Company was not found")
    })
    public ResponseEntity addEmployee(@PathVariable("companyId") Long companyId, @RequestBody Long employeeId) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if (!optionalCompany.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (!optionalEmployee.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Company company = optionalCompany.get();
        boolean employeeAlreadyInCompany = company.getEmployees().stream()
                .anyMatch(employee -> employee.getId().equals(employeeId));

        if (employeeAlreadyInCompany) {
            return ResponseEntity.badRequest().body("Employee with id=" + employeeId + " already in company with id=" + companyId);
        } else {
            company.getEmployees().add(optionalEmployee.get());
            companyRepository.save(company);
            return ResponseEntity.noContent().build();
        }
    }

    @ApiOperation(value = "Remove employee from company")
    @DeleteMapping("{companyId}/employees/{employeeId}")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Employee was removed from company"),
            @ApiResponse(code = 422, message = "Employee doesn't belong to company"),
            @ApiResponse(code = 404, message = "Company or employee was not found")
    })
    public ResponseEntity removeEmployee(@PathVariable("companyId") Long companyId, @PathVariable("employeeId") Long employeeId) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if (!optionalCompany.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (!optionalEmployee.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        Company company = optionalCompany.get();
        boolean employeeRemoved = company.getEmployees().remove(optionalEmployee.get());
        if (employeeRemoved) {
            companyRepository.save(company);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.unprocessableEntity().body("Employee with id=" + employeeId + " does not belong to company with id=" + companyId);
        }
    }
}

