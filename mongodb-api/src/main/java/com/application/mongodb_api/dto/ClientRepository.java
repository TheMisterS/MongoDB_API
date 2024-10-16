package com.application.mongodb_api.dto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends MongoRepository<Client,String>{
    @Query(sort = "{ '_id': -1 }")
    List<Client> findTopByOrderByIdDesc();
}