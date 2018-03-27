package org.example.swaggerdemo.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.example.swaggerdemo.entity.Employee;
import org.example.swaggerdemo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Api(value = EmployeeResource.RESOURCE_BASE_URI)
@RestController
@RequestMapping(EmployeeResource.RESOURCE_BASE_URI)
public class EmployeeResource {

    static final String RESOURCE_BASE_URI = "employees";

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeResource(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @ApiOperation(value = "Find all employees", response = Employee.class, responseContainer = "List")
    @GetMapping
    public ResponseEntity<List<Employee>> findAll() {
        return ResponseEntity.ok(employeeRepository.findAll());
    }

    @ApiOperation(value = "Find employee by id", response = Employee.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "Employee id", paramType = "path")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee was found"),
            @ApiResponse(code = 404, message = "Employee not found")
    })
    @GetMapping("{id}")
    public ResponseEntity<Employee> findById(@PathVariable final Long id) {
        final Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Update employee", response = Employee.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "Employee id", paramType = "path")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee updated"),
            @ApiResponse(code = 404, message = "Employee not found")
    })
    @PutMapping("{id}")
    public ResponseEntity put(@PathVariable final Long id, @RequestBody final Employee employee) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee employeeToUpdate = optionalEmployee.get();
            Employee updatedEmployee = employeeToUpdate.update(employee);
            employeeRepository.save(updatedEmployee);
            return ResponseEntity.ok(updatedEmployee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Create new employee")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Employee was created"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @PostMapping
    public ResponseEntity<Long> post(@RequestBody final Employee employee, UriComponentsBuilder uri) {
        final Long id = employeeRepository.save(employee).getId();
        final URI path = uri.path(RESOURCE_BASE_URI + "/" + id).build().toUri();
        return ResponseEntity.created(path).build();
    }

    @ApiOperation(value = "Delete employee")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "Employee id", paramType = "path")
    })
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable final Long id) {
        employeeRepository.deleteById(id);
    }
}
