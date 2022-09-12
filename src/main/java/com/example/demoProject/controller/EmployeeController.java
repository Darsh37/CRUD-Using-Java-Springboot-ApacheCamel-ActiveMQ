package com.example.demoProject.controller;

import com.example.demoProject.entity.Employee;
import com.example.demoProject.exception.ResourceNotFoundException;
import com.example.demoProject.model.EmployeeRequest;
import com.example.demoProject.services.EmployeeServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Configuration
@CrossOrigin(origins ="http://localhost:4200/")
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeServices employeeServices;

    @GetMapping("/all")
    public ResponseEntity<String> getData() throws JsonProcessingException {
        ResponseEntity<String> response1;
        try {
            String empsResponseString = employeeServices.getEmployeeData();
            response1 = ResponseEntity
                    .status(HttpStatus.OK).body(empsResponseString);
            return response1;
        } catch (ResourceNotFoundException r) {
            response1 = ResponseEntity.status(HttpStatus.OK).body("Not found");
            return response1;
        } catch (Exception e) {
            response1 = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception Occurred");
            return response1;
        }
    }

    @GetMapping("/emp/{id}")
    public ResponseEntity<String> getDataById(@PathVariable int id) throws JsonProcessingException {
        try {
            ResponseEntity<String> response;
            String empResponse = employeeServices.getEmpById(id);
            response = ResponseEntity.status(HttpStatus.OK).body(empResponse);
            return response;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<String> getDataByName(@PathVariable String name){
        try {
            ResponseEntity<String> response;
            String empName = employeeServices.getByName(name);
            response = ResponseEntity.status(HttpStatus.OK).body(empName);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteData(@PathVariable int id){
        ResponseEntity<String>response;
        employeeServices.deleteEmployeeData(id);
       return null;
    }

    @PostMapping("/emp")
    public ResponseEntity<String> addData(@RequestBody EmployeeRequest employeeRequest){
        ResponseEntity<String> response;
        String addEmployee = String.valueOf(employeeServices.newEmployeedata(employeeRequest));
        response = ResponseEntity.status(HttpStatus.OK).body(addEmployee);
        return response;
    }
}
