package com.example.backend.controller;

import com.example.backend.model.NoteItem;
import com.example.backend.repo.NoteRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/notes")
public class NoteController{
    @Autowired
    private NoteRepo noteRepo;

    @GetMapping
    public List<NoteItem> findAll(){
        return noteRepo.findAll();
    }

    @PostMapping
    public NoteItem save(@Validated @NonNull @RequestBody NoteItem noteItem){
        return noteRepo.save(noteItem);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id){
        noteRepo.deleteById(id);
    }
}
