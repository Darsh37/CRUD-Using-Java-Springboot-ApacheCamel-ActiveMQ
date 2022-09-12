package com.example.demoProject.controller;

import com.example.demoProject.exception.ResourceNotFoundException;
import com.example.demoProject.services.CompanyServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins ="http://localhost:4200/")
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    CompanyServices companyServices;

    @GetMapping("/all")
    public ResponseEntity<String> getAllcompData(){
        ResponseEntity<String> response;
        try {
            String compsResponseString =companyServices.getAllCompanyData();
            response =ResponseEntity.status(HttpStatus.OK).body(compsResponseString);
            return response;
        } catch (ResourceNotFoundException r) {
            response = ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Entity not found");
            return response;
        } catch (Exception e) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Exception Occurred");
            return response;
        }
    }
    @GetMapping("comp/{compId}")
    public ResponseEntity<String> getDataById(@PathVariable int compId) throws JsonProcessingException {
        try {
            String compRespString = companyServices.getCompanyDataById(compId);
            ResponseEntity<String> response= ResponseEntity.status(HttpStatus.OK).body(compRespString);
            return response;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
    @GetMapping("/{compName}")
    public ResponseEntity<String> getDataByName(@PathVariable String compName) throws JsonProcessingException {
        try {
            String compRespString1 = companyServices.getCompanyDataByName(compName);
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.OK).body(compRespString1);
            return response;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
