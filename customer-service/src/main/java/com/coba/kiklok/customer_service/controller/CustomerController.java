package com.saqu.obscf.customer_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "/api" )
public  class  CustomerController {

    @GetMapping( "/customers/{id}" )
    public String getCustomer(@PathVariable String id) {
        return  "Customer with ID: " + id;
    }

    @GetMapping( value = "/admin/customers" )
    public List<String> getAllCustomers() {
        return List.of( "Customer A" , "Customer B" );
    }
}
