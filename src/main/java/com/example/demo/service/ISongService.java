package com.example.demo.service;

import com.example.demo.model.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ISongService {
    Song save(Song song);
    void deleteById(Long id);
    Optional<Song> findById(Long id);
    Page<Song> findAll(Pageable pageable);
}
