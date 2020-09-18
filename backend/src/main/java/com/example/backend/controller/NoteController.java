package com.example.backend.controller;

import com.example.backend.model.NoteItem;
import com.example.backend.repo.NoteRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("notes")
public class NoteController {

    @Autowired
    private NoteRepo noteRepo;

    @GetMapping("all")
    public ResponseEntity<?> getAllNotes() {
        List<NoteItem> notes = new ArrayList<>();
        noteRepo.findAll().forEach(notes::add);
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public String removeNote(@PathVariable Long id) {
        noteRepo.deleteById(id);
        return id.toString();
    }

    @PostMapping("add")
    public ResponseEntity<?> addNote(@RequestBody NoteItem noteToAdd) {
        NoteItem addedNote = noteRepo.save(noteToAdd);
        return new ResponseEntity<>(addedNote, HttpStatus.OK);
    }
}
