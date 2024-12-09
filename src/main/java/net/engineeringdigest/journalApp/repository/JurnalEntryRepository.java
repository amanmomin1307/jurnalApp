package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.Entity.JurnalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
public interface JurnalEntryRepository extends MongoRepository<JurnalEntry, ObjectId> {

}
