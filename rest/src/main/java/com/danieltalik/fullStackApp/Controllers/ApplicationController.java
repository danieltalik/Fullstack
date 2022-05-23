package com.danieltalik.fullStackApp.Controllers;

import com.danieltalik.fullStackApp.Models.PersonUpdate;
import com.danieltalik.fullStackApp.Models.PersonRequest;
import com.danieltalik.fullStackApp.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ApplicationController {
    private PersonService service;

    @Autowired
    public ApplicationController(PersonService service) {
        this.service = service;
    }

    @RequestMapping(value = "/submitNewPerson", method= RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addPerson(@RequestBody PersonRequest request){
        service.addPerson(request);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("Result", "Added person with the name " + request.getFirstName() + " " + request.getLastName() +" to the h2 DB");
        return new ResponseEntity<Object>(node, HttpStatus.CREATED);
    }
    @RequestMapping(value = "/getAllPeople", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllPeople() throws Exception {
        try{
            Iterable<PersonUpdate> result = service.getAllPeople();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e){
            throw new Exception("Cannot retrieve all people");
        }
    }

}
