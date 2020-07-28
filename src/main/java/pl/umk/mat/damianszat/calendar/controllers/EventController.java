package pl.umk.mat.damianszat.calendar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.umk.mat.damianszat.calendar.model.Event;
import pl.umk.mat.damianszat.calendar.services.EventService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    public final EventService eventService;

    @Autowired
    public EventController(final EventService eventService){
        this.eventService = eventService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Event save(@RequestBody @Valid @NotNull Event event){
        return eventService.save(event);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Event> getAll(){
        return eventService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Event getOne(@PathVariable String id){
        return eventService.findOne(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOne(@PathVariable String id){
        eventService.deleteOne(id);

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
