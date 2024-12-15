package net.engineeringdigest.journalApp.service;

//import jdk.jpackage.internal.Log;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.Entity.JurnalEntry;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.repository.JurnalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class JurnalEntryService {

    @Autowired
    private JurnalEntryRepository jurnalEntryRepository;

    @Autowired
    private UserService userService;

    public void saveEntry(JurnalEntry jurnalEntry, String userName){
        try {
            User user = userService.findByUserName(userName);
            jurnalEntry.setDate(LocalDateTime.now());
            JurnalEntry saved = jurnalEntryRepository.save(jurnalEntry);
            user.getJurnalEntries().add(saved);
            userService.saveEntry(user);
        }catch (Exception e){
            log.error("Exception", e);
        }
    }


    public void saveEntry(JurnalEntry jurnalEntry){
        jurnalEntryRepository.save(jurnalEntry);
    }

    public List<JurnalEntry> getAll(){
        return jurnalEntryRepository.findAll();
    }

    public Optional<JurnalEntry> findById(ObjectId id){
        return jurnalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id, String userName){
        User user = userService.findByUserName(userName);
        user.getJurnalEntries().removeIf(x -> x.getId().equals(id));
        userService.saveEntry(user);
        jurnalEntryRepository.deleteById(id);
    }
}
