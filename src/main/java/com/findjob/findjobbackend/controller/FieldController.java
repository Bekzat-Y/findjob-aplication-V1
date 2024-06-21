package com.findjob.findjobbackend.controller;


import com.findjob.findjobbackend.model.Field;
import com.findjob.findjobbackend.service.field.IFieldService;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/field")
@AllArgsConstructor
public class FieldController {

    private final IFieldService fieldService;

    @GetMapping("/showAll")
    public ResponseEntity<Iterable<Field>> showAll(){
        return new ResponseEntity<>(fieldService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Field> findById(@PathVariable Long id){
        Optional<Field> fieldOptional = fieldService.findById(id);
        return fieldOptional.map(field -> new ResponseEntity<>(field, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping("/create")
    public ResponseEntity<Field> createField(@RequestBody Field field){
        return new ResponseEntity<>(fieldService.save(field), HttpStatus.CREATED);
    }


    @GetMapping("/delete/{id}")
    public ResponseEntity<Field> deleteField(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        Optional<Field> fieldOptional = fieldService.findById(id);
        if (fieldOptional.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
            fieldService.deleteById(id);
        return new ResponseEntity<>(fieldOptional.get(),HttpStatus.OK);
    }
}
