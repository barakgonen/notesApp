package com.example.backend.repo;

import com.example.backend.model.NoteItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepo extends JpaRepository<NoteItem, Long> {
}
