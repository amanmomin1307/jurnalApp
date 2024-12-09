package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.Entity.JurnalEntry;
import net.engineeringdigest.journalApp.repository.JurnalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JurnalEntryService {

    @Autowired
    private JurnalEntryRepository jurnalEntryRepository;

    public void saveEntry(JurnalEntry jurnalEntry){
        jurnalEntryRepository.save(jurnalEntry);
    }

    public List<JurnalEntry> getAll(){
        return jurnalEntryRepository.findAll();
    }

    public Optional<JurnalEntry> findById(ObjectId id){
        return jurnalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id){
        jurnalEntryRepository.deleteById(id);
    }
}
