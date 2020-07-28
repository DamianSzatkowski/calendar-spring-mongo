package pl.umk.mat.damianszat.calendar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.umk.mat.damianszat.calendar.model.Event;
import pl.umk.mat.damianszat.calendar.model.User;
import pl.umk.mat.damianszat.calendar.repositories.EventRepository;
import pl.umk.mat.damianszat.calendar.services.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService, final EventRepository eventRepository){
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAll(){
        return userService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getOne(@PathVariable String id){
        return userService.findOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@RequestBody @Valid @NotNull User user){
        return userService.save(user);
    }

    @PostMapping("/{id}/event")
    @ResponseStatus(HttpStatus.CREATED)
    public User addEvent(@RequestBody @Valid @NotNull Event event, @PathVariable("id") String userId){
        return userService.addEventToUser(event, userId);
    }

    @DeleteMapping("/{userId}/user/{eventId}/event")
    public ResponseEntity<Void> deleteEventFromUser(@PathVariable String userId, @PathVariable String eventId){
        userService.deleteEventOfUser(eventId, userId);

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public User patch(@RequestBody @NotNull User user){
        return userService.patchOne(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOne(@PathVariable String id){
        userService.deleteOne(id);

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
