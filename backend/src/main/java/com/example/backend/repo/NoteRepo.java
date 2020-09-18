package com.example.backend.repo;

import com.example.backend.model.NoteItem;

import org.springframework.data.repository.CrudRepository;

public interface NoteRepo extends CrudRepository<NoteItem, Long> {
}
