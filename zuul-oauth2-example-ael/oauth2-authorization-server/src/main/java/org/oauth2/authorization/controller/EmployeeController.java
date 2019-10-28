package org.oauth2.authorization.controller;

import java.util.ArrayList;
import java.util.List;

import org.oauth2.authorization.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Optional;

@Api( description="API pour les opérations CRUD sur les produits.")
@Controller
public class EmployeeController {

    private List<Employee> employees = new ArrayList<>();
    
    @ApiOperation(value = "Récupère le mail  ")
    @GetMapping("/employee")
    @ResponseBody
    public Optional<Employee> getEmployee(@RequestParam String email) {
        return employees.stream().filter(x -> x.getEmail().equals(email)).findAny();
    }
    
    @ApiOperation(value = "l employe en presantant l' access token ")
    @PostMapping(value = "/employee", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void postMessage(@RequestBody Employee employee) {
        employees.add(employee);
    }

}
