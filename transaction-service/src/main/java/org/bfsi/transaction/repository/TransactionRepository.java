package org.bfsi.transaction.repository;

import org.bfsi.transaction.entity.TransactionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends MongoRepository<TransactionEntity, String> {
}
