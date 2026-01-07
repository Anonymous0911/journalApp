package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.entity.entry;
import com.example.demo.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {


    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public void saveJournalEntry(entry journalEntry, String userName) {
        try {
            User user = userService.findByUsername(userName);
            journalEntry.setDate(LocalDateTime.now());
            entry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);

            userService.saveUser(user);
        } catch (Exception e) {
                throw new RuntimeException("An error occurred while saving.",e);
        }

    }
    public void saveJournalEntry(entry journalEntry) {

        journalEntryRepository.save(journalEntry);
    }
    public List<entry> getALL(){
        return journalEntryRepository.findAll();
    }
    public Optional<entry> findById(ObjectId id){
        return journalEntryRepository.findById(id);

    }

    @Transactional
    public boolean deleteById(ObjectId id,String userName){
        boolean removed = false;
        try {
            User user= userService.findByUsername(userName);
            removed=user.getJournalEntries().removeIf(entry -> entry.getId().equals(id));
            if(removed){

            userService.saveUser(user);
            journalEntryRepository.deleteById(id);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occurred while deleting.",e);
        }
        return removed;
    }

}
