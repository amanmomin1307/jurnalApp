package net.engineeringdigest.journalApp.Controller;

import net.engineeringdigest.journalApp.Entity.JurnalEntry;
import net.engineeringdigest.journalApp.service.JurnalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JurnalEnteryController {

    @Autowired
    private JurnalEntryService jurnalEntryService;

    private Map<ObjectId, JurnalEntry> jurnalEntries = new HashMap<ObjectId, JurnalEntry>();

    
    @GetMapping
    public ResponseEntity<?> getAll(){
        List<JurnalEntry> all = jurnalEntryService.getAll();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JurnalEntry> createEntry(@RequestBody JurnalEntry myEntry){
        try {
            myEntry.setDate(LocalDateTime.now());
            jurnalEntryService.saveEntry(myEntry);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JurnalEntry> getJurnalEntityById(@PathVariable ObjectId myId){
        Optional<JurnalEntry> jurnalEntry = jurnalEntryService.findById(myId);
        if(jurnalEntry.isPresent()){
            return new ResponseEntity<>(jurnalEntry.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJurnalEntityById(@PathVariable ObjectId myId){
         jurnalEntryService.deleteById(myId);
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<?>  updateJurnalById(@PathVariable ObjectId id, @RequestBody JurnalEntry newEntry){
        JurnalEntry old = jurnalEntryService.findById(id).orElse(null);

        if(old != null) {
            old.setTitle(newEntry.getId() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getTitle().equals("") ? newEntry.getContent() : old.getContent());
            jurnalEntryService.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    
}
