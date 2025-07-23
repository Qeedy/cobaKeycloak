package com.coba.kiklok.customer_service.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "/api" )
public  class  CustomerController {

    @GetMapping( "/customers/{id}" )
    @PreAuthorize( "hasAnyRole('USER', 'ADMIN')" )
    public String getCustomer(@PathVariable String id) {
        return  "Customer with ID: " + id;
    }

    @GetMapping( value = "/admin/customers" )
    @PreAuthorize( "hasAnyRole('ADMIN')" )
    public List<String> getAllCustomers() {
        return List.of( "Customer A" , "Customer B" );
    }
}
