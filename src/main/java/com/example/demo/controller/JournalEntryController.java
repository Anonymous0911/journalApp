package com.example.demo.controller;
import com.example.demo.entity.User;
import com.example.demo.entity.entry;
import com.example.demo.service.JournalEntryService;
import com.example.demo.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
    @RequestMapping("/journal")
    public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

        @GetMapping
        public ResponseEntity<?> getAllJournalEntriesOfUser() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User user= userService.findByUsername(userName);
            List<entry> all= user.getJournalEntries();
            if(all!=null && !all.isEmpty()){
                return new ResponseEntity<>(all, HttpStatus.OK);

            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        @PostMapping
        public ResponseEntity<entry> save(@RequestBody entry entry) {
            try {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String userName = authentication.getName();

            journalEntryService.saveJournalEntry(entry,userName);
            return new ResponseEntity<>(entry,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}
        }
        @GetMapping("id/{id}")
        public ResponseEntity<?> getById(@PathVariable ObjectId id){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User user=userService.findByUsername(userName);
            List<entry> collect=user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
            if(!collect.isEmpty()){
            Optional<entry> Entry= journalEntryService.findById(id);
                if(Entry.isPresent()){
                    return new ResponseEntity<>(Entry.get(), HttpStatus.OK);
                }
             }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        @PutMapping("id/{id}")
        public ResponseEntity<?> update(@PathVariable ObjectId id, @RequestBody entry newentry) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User user=userService.findByUsername(userName);
            List<entry> collect=user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
            if(!collect.isEmpty()){
                Optional<entry> Entry= journalEntryService.findById(id);
                if(Entry.isPresent()){
                    entry old=Entry.get();
                    old.setTitle(newentry.getTitle() != null && !newentry.getTitle().equals("") ? newentry.getTitle() : old.getTitle());
                    old.setDescription(newentry.getDescription() != null && !newentry.getDescription().equals("") ? newentry.getDescription() : old.getDescription());
                    journalEntryService.saveJournalEntry(old);
                    return new ResponseEntity<>(old, HttpStatus.OK);
            }

            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        @DeleteMapping("id/{id}")
        public ResponseEntity<?> delete(@PathVariable ObjectId id) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
           boolean removed= journalEntryService.deleteById(id,userName);
           if(removed) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
           else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


