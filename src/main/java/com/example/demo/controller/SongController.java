package com.example.demo.controller;

import com.example.demo.dto.response.ResponMessage;
import com.example.demo.model.Song;
import com.example.demo.service.impl.SongServiceImpl;
import org.graalvm.compiler.phases.OptimisticOptimizations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("song")
public class SongController {
    @Autowired
    SongServiceImpl songService;
    @PostMapping
    public ResponseEntity<?> createSong(@Valid @RequestBody Song song){
        if(song.getAvatarSong()==null||song.getAvatarSong().trim().isEmpty()){
            return new ResponseEntity<>(new ResponMessage("noavatar"), HttpStatus.OK);
        }
        if(song.getMp3Url()==null||song.getMp3Url().trim().isEmpty()){
            return new ResponseEntity<>(new ResponMessage("nomp3url"), HttpStatus.OK);
        }
        songService.save(song);
        return new ResponseEntity<>(new ResponMessage("yes"),HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<?> pageSong(@PageableDefault(sort = "nameSong", direction = Sort.Direction.ASC)Pageable pageable){
        Page<Song> songPage = songService.findAll(pageable);
        if(songPage.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(songPage, HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteSong(@PathVariable Long id){
        Optional<Song> song = songService.findById(id);
        if(!song.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        songService.deleteById(song.get().getId());
        return new ResponseEntity<>(new ResponMessage("yes"), HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<?> detailSong(@PathVariable Long id){
        Optional<Song> song = songService.findById(id);
        if(!song.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        songService.findById(id);
        return new ResponseEntity<>(song, HttpStatus.OK);
    }
}
