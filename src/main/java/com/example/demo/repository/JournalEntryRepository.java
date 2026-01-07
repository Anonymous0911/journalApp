package com.example.demo.repository;

import com.example.demo.entity.entry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<entry, ObjectId> {

}
