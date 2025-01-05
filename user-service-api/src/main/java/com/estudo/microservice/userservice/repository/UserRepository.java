package com.estudo.microservice.userservice.repository;

import com.estudo.microservice.userservice.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
}
