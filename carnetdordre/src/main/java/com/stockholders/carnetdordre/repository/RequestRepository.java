package com.stockholders.carnetdordre.repository;

import com.stockholders.carnetdordre.domain.Request;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Request entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestRepository extends MongoRepository<Request, String> {

}
