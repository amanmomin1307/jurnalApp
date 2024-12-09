package net.engineeringdigest.journalApp.Controller;

import net.engineeringdigest.journalApp.Entity.JurnalEntry;
import net.engineeringdigest.journalApp.service.JurnalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JurnalEnteryController {

    @Autowired
    private JurnalEntryService jurnalEntryService;

    private Map<ObjectId, JurnalEntry> jurnalEntries = new HashMap<ObjectId, JurnalEntry>();

    
    @GetMapping
    public List<JurnalEntry> getAll(){
        return new ArrayList<>(jurnalEntryService.getAll());
    }

    @PostMapping
    public JurnalEntry createEntry(@RequestBody JurnalEntry myEntry){
        myEntry.setDate(LocalDateTime.now());
        jurnalEntryService.saveEntry(myEntry);
        return myEntry;
    }

    @GetMapping("id/{myId}")
    public JurnalEntry getJurnalEntityById(@PathVariable ObjectId myId){
        return jurnalEntryService.findById(myId).orElse(null);
    }

    @DeleteMapping("id/{myId}")
    public Boolean deleteJurnalEntityById(@PathVariable ObjectId myId){
         jurnalEntryService.deleteById(myId);
         return true;
    }

    @PutMapping("id/{id}")
    public JurnalEntry updateJurnalById(@PathVariable ObjectId id, @RequestBody JurnalEntry newEntry){
        JurnalEntry old = jurnalEntryService.findById(id).orElse(null);

        if(old != null){
            old.setTitle(newEntry.getId() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getTitle().equals("") ? newEntry.getContent() : old.getContent());
        }

        jurnalEntryService.saveEntry(old);

        return old;
    }


    
}
