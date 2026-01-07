package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.entity.entry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUserName(String userName);

    void deleteByUserName(String userName);
}
